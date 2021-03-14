using System;
using System.IO;
using System.Linq;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Nest;
using PizzaStore.Model.Entity;
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

            Model.PizzaStoreMetaData.Budget = Convert.ToInt32(configuration["PizzaStoreBudget"]);
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

            Material[] materials = SeedMaterialIndex(elasticClient, configuration);
            SeedMenuIndex(elasticClient, configuration, materials);
        }

        private static Material[] SeedMaterialIndex(IElasticClient client, IConfiguration configuration)
        {
            var initialMaterialData = configuration.GetSection("InitialMaterialData").Get<Material[]>();
            client.IndexManyWithIndexExistenceControl(configuration["MaterialIndexName"], initialMaterialData);

            return initialMaterialData;
        }

        private static void SeedMenuIndex(IElasticClient client, IConfiguration configuration, Material[] menuItemMaterials)
        {
            var initialMenuItemData = configuration.GetSection("InitialMenuData").Get<MenuItem[]>();

            foreach (var menuItem in initialMenuItemData)
            {
                if (menuItem.RequiredMaterials is null || menuItem.RequiredMaterials.Count() <= 0)
                    continue;

                foreach (var requiredMaterial in menuItem.RequiredMaterials)
                    requiredMaterial.Id = menuItemMaterials.Where(material => material.Name == requiredMaterial.Name)?.FirstOrDefault()?.Id;
            }

            client.IndexManyWithIndexExistenceControl(configuration["MenuIndexName"], initialMenuItemData);
        }
    }
}