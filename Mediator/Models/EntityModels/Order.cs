using System;
using System.Collections.Generic;

namespace PizzaStore.Model.Entity
{
    public class Order
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public CreditCard CreditCard { get; set; }
        public List<OrderedMenuItem> OrderedItems { get; set; }
    }
}