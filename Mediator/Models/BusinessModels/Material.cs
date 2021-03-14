namespace PizzaStore.Model.Business
{
    public class Material
    {
        public string Id { get; private set; }
        public string Name { get; set; }
        public string Type { get; set; }
        public decimal Price { get; set; }
        public int QuantityInStock { get; set; }
    }
}