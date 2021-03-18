using System;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Nest;
using PizzaStore.Service;

namespace PizzaStore
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services)
        {
            services.AddSingleton<IElasticClient, ElasticClient>(sp => {
                var elasticClient = new ElasticClient(new ConnectionSettings(new Uri(Configuration["ElasticsearchURL"])));

                bool mediatorConnectToElasticsearch = elasticClient.Ping().IsValid;

                if(!mediatorConnectToElasticsearch) {
                    throw new Exception("Couldn't connect to Elasticsearch!");
                }

                return elasticClient;
            });
            services.AddSingleton<IPizzaStoreService, PizzaStoreService>();
            services.AddControllers().AddNewtonsoftJson();
        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHttpsRedirection();
            }

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
