using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class MenuItem
    {
        public string Name { get; set; }
        public MenuItemType Type { get; set; }
        public List<Material> RequiredMaterials { get; set; }
        public int PreparationTimeInMinute { get; set; }
        public decimal Price { get; set; }
    }
}