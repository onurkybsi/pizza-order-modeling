using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class UpdateMaterialsQuantitiesRequest
    {
        public Dictionary<string, int> MaterialsIdsWithQuantities { get; set; }
    }
}