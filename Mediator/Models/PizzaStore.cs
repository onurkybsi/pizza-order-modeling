namespace PizzaStore.Model
{
    public class PizzaStore
    {
        public decimal Budget { get; set; }
        public PizzaStoreMenu Menu { get; set; }
        public PizzaStoreStock Stock { get; set; }
    }
}