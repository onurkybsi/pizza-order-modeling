using System;
using System.Collections.Generic;

namespace PizzaStore.Model.Entity
{
    public class MenuItem
    {
        public string Id { get; set; } = Guid.NewGuid().ToString();
        public string Name { get; set; }
        public MenuItemType Type { get; set; }
        public List<MenuItemRequiredMaterial> RequiredMaterials { get; set; }
        public int PreparationTimeInMinute { get; set; }
        public decimal Price { get; set; }
    }
}