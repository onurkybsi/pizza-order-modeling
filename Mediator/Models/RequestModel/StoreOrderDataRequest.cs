using System.Collections.Generic;
using PizzaStore.Model.Entity;

namespace PizzaStore.Model
{
    public class StoreOrderDataRequest
    {
        public CreditCard CreditCardInfo { get; set; }
        public List<OrderedMenuItem> OrderedMenuItems { get; set; }
    }
}