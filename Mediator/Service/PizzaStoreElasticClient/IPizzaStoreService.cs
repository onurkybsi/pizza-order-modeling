using System.Collections.Generic;
using PizzaStore.Model;
using PizzaStore.Model.Business;

namespace PizzaStore.Service
{
    public interface IPizzaStoreService
    {
        List<MenuItem> GetMenu();
        List<Material> GetMaterials();
        List<MenuItem> GetMenuItemsByIds(GetMenuItemsByIdsRequest request);
        List<Material> GetMaterialsByIds(GetMaterialsByIdsRequest request);
    }
}