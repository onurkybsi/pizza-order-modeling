namespace PizzaStore.Model.Entity
{
    public class CreditCard
    {
        public string NameOnCard { get; set; }
        public long CardNumber { get; set; }
        public int ExpirationMonth { get; set; }
        public int ExpirationYear { get; set; }
        public int CVV { get; set; }
    }
}