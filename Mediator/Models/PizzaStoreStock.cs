using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class PizzaStoreStock
    {
        public Dictionary<string, int> PizzaMaterials { get; set; }
        public Dictionary<string, int> Drinks { get; set; }
    }
}