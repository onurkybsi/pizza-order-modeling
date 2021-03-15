using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.Configuration;
using Nest;
using PizzaStore.Model;
using PizzaStore.Model.Business;
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

        public List<Business.MenuItem> GetMenuItemsByIds(GetMenuItemsByIdsRequest request)
        {
            if (request is null || request.MenuItemIds is null || request.MenuItemIds.Count <= 0)
                return new List<Business.MenuItem>();

            List<Func<QueryContainerDescriptor<Entity.MenuItem>, QueryContainer>> shouldQueries = new List<Func<QueryContainerDescriptor<Entity.MenuItem>, QueryContainer>>();
            request.MenuItemIds.ForEach(id =>
            {
                shouldQueries.Add(s => s.Match(m => m.Field(f => f.Id).Query(id)));
            });

            var searchResponse = _elasticClient.Search<Entity.MenuItem>(s => s.Index(_configuration["MenuIndexName"])
                .Query(q =>
                    q.Bool(bq =>
                        bq.Should(shouldQueries))
                ));

            List<Business.MenuItem> menuItems = searchResponse?.Documents?.MapTo<List<Business.MenuItem>>() ?? new List<Business.MenuItem>();

            return menuItems;
        }

        public List<Business.Material> GetMaterialsByIds(GetMaterialsByIdsRequest request)
        {
            if (request is null || request.MaterialsIds is null || request.MaterialsIds.Count <= 0)
                return new List<Business.Material>();

            List<Func<QueryContainerDescriptor<Entity.Material>, QueryContainer>> shouldQueries = new List<Func<QueryContainerDescriptor<Entity.Material>, QueryContainer>>();
            request.MaterialsIds.ForEach(id =>
            {
                shouldQueries.Add(s => s.Match(m => m.Field(f => f.Id).Query(id)));
            });

            var searchResponse = _elasticClient.Search<Entity.Material>(s => s.Index(_configuration["MaterialIndexName"])
                .Query(q =>
                    q.Bool(bq =>
                        bq.Should(shouldQueries))
                ));

            List<Business.Material> materials = searchResponse?.Documents?.MapTo<List<Business.Material>>() ?? new List<Business.Material>();

            return materials;
        }

        public StoreMetaData GetStoreBudget()
        {
            var searchResponse = _elasticClient.Search<Entity.StoreMetaData>(s => s.Index(_configuration["StoreIndexName"])
                .Query(q =>
                    q.Bool(bq =>
                        bq.Must(must => must.Match(m => m.Field(f => f.IdentifierName).Query(Constants.BudgetMetaDataIdentifierName))))
                ));

            Business.StoreMetaData budgetMetaData = searchResponse?.Documents?.FirstOrDefault().MapTo<Business.StoreMetaData>();

            return budgetMetaData;
        }

        public UpdateStoreBudgetResponse UpdateStoreBudget(UpdateStoreBudgetRequest request)
        {
            UpdateStoreBudgetResponse response = new UpdateStoreBudgetResponse();
            try
            {
                var storeBudget = GetStoreBudget().MapTo<Entity.StoreMetaData>();
                storeBudget.Value = request.Amount.ToString();
                var updateResponse = _elasticClient.Update<Entity.StoreMetaData>(storeBudget.Id, i => i.Index(_configuration["StoreIndexName"]).Doc(storeBudget));

                response.IsSuccess = updateResponse.IsValid;
            }
            catch (System.Exception ex)
            {
                response.IsSuccess = false;
                response.Message = ex.Message.ToString();
            }

            return response;
        }
    }
}