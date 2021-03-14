using System;
using System.Collections.Generic;
using PizzaStore.Model.Entity;

namespace PizzaStore.Model.Business
{
    public class MenuItem
    {
        public string Id { get; private set; }
        public string Name { get; set; }
        public string Type { get; set; }
        public List<MenuItemRequiredMaterial> RequiredMaterials { get; set; }
        public int PreparationTimeInMinute { get; set; }
        public decimal Price { get; set; }
    }
}