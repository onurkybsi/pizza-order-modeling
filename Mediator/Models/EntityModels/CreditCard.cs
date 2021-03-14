namespace PizzaStore.Model.Entity
{
    public class CreditCard
    {
        public string NameOnCard { get; set; }
        public long CreditCardNumber { get; set; }
        public byte ExpirationMonth { get; set; }
        public byte ExpirationYear { get; set; }
        public byte CVV { get; set; }
    }
}