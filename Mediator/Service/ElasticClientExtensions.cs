using System.Collections.Generic;
using Nest;

namespace PizzaStore.Service
{
    public static class ElasticClientExtensions
    {
        public static void IndexManyWithIndexExistenceControl<TDoc>(this IElasticClient elasticClient, string indexName, List<TDoc> documents) where TDoc : class
        {
            if (!elasticClient.Indices.Exists(indexName).Exists)
            {
                elasticClient.Indices.Create(indexName, d => d.Map<TDoc>(map => map.AutoMap()));
                elasticClient.IndexMany<TDoc>(documents, indexName);
            }
        }
        public static void IndexManyWithIndexExistenceControl<TDoc>(this IElasticClient elasticClient, string indexName, TDoc[] documents) where TDoc : class
        {
            if (!elasticClient.Indices.Exists(indexName).Exists)
            {
                elasticClient.Indices.Create(indexName, d => d.Map<TDoc>(map => map.AutoMap()));
                elasticClient.IndexMany<TDoc>(documents, indexName);
            }
        }
    }
}