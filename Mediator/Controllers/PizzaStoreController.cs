using Microsoft.AspNetCore.Mvc;

namespace PizzaStore.Controller
{
    public class PizzaStoreController : ControllerBase
    {
        public PizzaStoreController() { }

        [HttpGet]
        public IActionResult GetPizzaStoreMenu()
        {
            return Ok();
        }

        [HttpPost]
        public IActionResult CreateOrder([FromBody] Model.Order order)
        {
            return Ok();
        }
    }
}