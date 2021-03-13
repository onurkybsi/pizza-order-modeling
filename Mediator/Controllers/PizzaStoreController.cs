using Microsoft.AspNetCore.Mvc;

namespace PizzaStore.Controller
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class PizzaStoreController : ControllerBase
    {
        private readonly Nest.IElasticClient _client;
        public PizzaStoreController(Nest.IElasticClient client)
        {
            _client = client;
        }

        [HttpGet]
        public IActionResult GetMenu()
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