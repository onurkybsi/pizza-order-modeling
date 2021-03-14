using Microsoft.AspNetCore.Mvc;
using PizzaStore.Service;

namespace PizzaStore.Controller
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class PizzaStoreController : ControllerBase
    {
        private readonly IPizzaStoreService _pizzaStoreService;

        public PizzaStoreController(IPizzaStoreService pizzaStoreService)
        {
            _pizzaStoreService = pizzaStoreService;
        }

        [HttpGet]
        public IActionResult GetMenu()
        {
            return Ok(_pizzaStoreService.GetMenu());
        }

        [HttpGet]
        public IActionResult GetMaterials()
        {
            return Ok(_pizzaStoreService.GetMaterials());
        }

        [HttpPost]
        public IActionResult CreateOrder()
        {
            // TO-DO
            return Ok();
        }
    }
}