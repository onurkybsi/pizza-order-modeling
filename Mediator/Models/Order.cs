using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class Order
    {
        public string Id { get; set; }
        public CreditCard CreditCard { get; set; }
        public List<MenuItem> OrderedItems { get; set; }
    }
}