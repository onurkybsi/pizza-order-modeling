using Microsoft.AspNetCore.Mvc;
using PizzaStore.Model;
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

        [HttpGet]
        public IActionResult GetMenuItemsByIds([FromQuery] GetMenuItemsByIdsRequest request)
        {
            if (request is null)
                return BadRequest();

            return Ok(_pizzaStoreService.GetMenuItemsByIds(request));
        }

        [HttpGet]
        public IActionResult GetMaterialsByIds([FromQuery] GetMaterialsByIdsRequest request)
        {
            if (request is null)
                return BadRequest();

            return Ok(_pizzaStoreService.GetMaterialsByIds(request));
        }

        [HttpPost]
        public IActionResult UpdateBudget(UpdateBudgetRequest request)
        {
            if (request is null)
                return BadRequest();

            PizzaStore.Model.PizzaStoreMetaData.Budget += request.Amount;

            return Ok();
        }

        [HttpPost]
        public IActionResult CreateOrder()
        {
            // TO-DO
            return Ok();
        }
    }
}