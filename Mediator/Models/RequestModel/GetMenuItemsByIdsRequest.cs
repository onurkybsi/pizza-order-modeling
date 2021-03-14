using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class GetMenuItemsByIdsRequest
    {
        public List<string> MenuItemIds { get; set; }
    }
}