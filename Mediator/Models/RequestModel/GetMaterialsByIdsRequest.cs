using System.Collections.Generic;

namespace PizzaStore.Model
{
    public class GetMaterialsByIdsRequest
    {
        public List<string> MaterialsIds { get; set; }
    }
}