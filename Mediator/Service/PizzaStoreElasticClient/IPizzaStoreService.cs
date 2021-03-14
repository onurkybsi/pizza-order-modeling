using System.Collections.Generic;
using PizzaStore.Model.Business;

namespace PizzaStore.Service
{
    public interface IPizzaStoreService
    {
        List<MenuItem> GetMenu();
        List<Material> GetMaterials();
    }
}