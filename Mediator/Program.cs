using System;
using System.Collections.Generic;
using System.IO;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Nest;
using PizzaStore.Service;

namespace PizzaStore
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Helper.LoadEnvFile(Path.Combine(Directory.GetCurrentDirectory(), ".env"));
            var configuration = Helper.CreateConfigurationWithEnvironmentVariables(Directory.GetCurrentDirectory(), Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT"));
            IHost host = CreateHostBuilder(args, configuration).Build();

            using (var scope = host.Services.CreateScope())
            {
                SeedData(scope.ServiceProvider);
            }

            host.Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args, IConfiguration configuration)
            => Host.CreateDefaultBuilder(args)
                    .ConfigureAppConfiguration(x => x.AddConfiguration(configuration))
                    .ConfigureWebHostDefaults(webBuilder =>
                    {
                        webBuilder.UseKestrel(options => options.AddServerHeader = false);
                        webBuilder.UseStartup<Startup>();
                    });

        private static void SeedData(IServiceProvider services)
        {
            var elasticClient = services.GetRequiredService<IElasticClient>();
            var configuration = services.GetRequiredService<IConfiguration>();

            var initialMaterialData = configuration.GetSection("InitialMaterialData").Get<Model.Material[]>();
            elasticClient.IndexManyWithIndexExistenceControl(configuration["MaterialIndexName"], initialMaterialData);

            var initialMenuItemData = configuration.GetSection("InitialMenuData").Get<Model.MenuItem[]>();
            elasticClient.IndexManyWithIndexExistenceControl(configuration["MenuIndexName"], initialMenuItemData);
        }
    }
}