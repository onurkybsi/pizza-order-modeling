using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class Pizza
    {
        public string Name { get; set; }
        public Dictionary<string, int> Materials { get; set; }
        public byte PreparationTimeInMinutes { get; set; }
    }
}