using System.Collections.Generic;
using Microsoft.Extensions.Configuration;
using Nest;
using Business = PizzaStore.Model.Business;
using Entity = PizzaStore.Model.Entity;

namespace PizzaStore.Service
{
    public class PizzaStoreService : IPizzaStoreService
    {
        private readonly IElasticClient _elasticClient;
        private readonly IConfiguration _configuration;

        public PizzaStoreService(IElasticClient elasticClient, IConfiguration configuration)
        {
            _elasticClient = elasticClient;
            _configuration = configuration;
        }

        public List<Business.MenuItem> GetMenu()
        {
            var searchResponse = _elasticClient.Search<Entity.MenuItem>(s => s.Index(_configuration["MenuIndexName"]).Query(q => q.MatchAll()));

            List<Business.MenuItem> menu = searchResponse?.Documents?.MapTo<List<Business.MenuItem>>() ?? new List<Business.MenuItem>();

            return menu;
        }

        public List<Business.Material> GetMaterials()
        {
            var searchResponse = _elasticClient.Search<Entity.Material>(s => s.Index(_configuration["MaterialIndexName"]).Query(q => q.MatchAll()));

            List<Business.Material> materials = searchResponse?.Documents?.MapTo<List<Business.Material>>() ?? new List<Business.Material>();

            return materials;
        }
    }
}