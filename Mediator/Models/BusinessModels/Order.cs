using System.Collections.Generic;
using PizzaStore.Model.Entity;

namespace PizzaStore.Model.Business
{
    public class Order
    {
        public CreditCard CreditCard { get; set; }
        public List<MenuItem> OrderedItems { get; set; }
    }
}