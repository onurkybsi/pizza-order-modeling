using System;

namespace PizzaStore.Model.Entity
{
    public class Material
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string Name { get; set; }
        public MaterialType Type { get; set; }
        public decimal Price { get; set; }
        public int QuantityInStock { get; set; }
    }
}