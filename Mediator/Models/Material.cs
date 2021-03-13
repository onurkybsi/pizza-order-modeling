namespace PizzaStore.Model
{
    public class Material
    {
        public string Name { get; set; }
        public MaterialType Type { get; set; }
        public decimal Price { get; set; }
        public int QuantityInStock { get; set; }
    }
}