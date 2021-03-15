using System;

namespace PizzaStore.Model.Entity
{
    public class StoreMetaData
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string IdentifierName { get; set; }
        public string Value { get; set; }
    }
}