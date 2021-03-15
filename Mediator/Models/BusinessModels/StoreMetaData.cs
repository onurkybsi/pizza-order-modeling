using System;

namespace PizzaStore.Model.Business
{
    public class StoreMetaData
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string IdentifierName { get; set; }
        public object Value { get; set; }
    }
}