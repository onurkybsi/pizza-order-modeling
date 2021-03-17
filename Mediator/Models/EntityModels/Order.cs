using System;
using System.Collections.Generic;

namespace PizzaStore.Model.Entity
{
    public class Order
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public CreditCard CreditCardInfo { get; set; }
        public List<OrderedMenuItem> OrderedMenuItems { get; set; }
    }
}