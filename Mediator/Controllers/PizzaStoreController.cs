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
            => Ok(_pizzaStoreService.GetMenu());

        [HttpGet]
        public IActionResult GetMenuItemsByIds([FromQuery] GetMenuItemsByIdsRequest request)
        {
            if (request is null)
                return BadRequest();

            return Ok(_pizzaStoreService.GetMenuItemsByIds(request));
        }

        [HttpGet]
        public IActionResult GetMaterials()
            => Ok(_pizzaStoreService.GetMaterials());

        [HttpGet]
        public IActionResult GetMaterialsByIds([FromQuery] GetMaterialsByIdsRequest request)
        {
            if (request is null)
                return BadRequest();

            return Ok(_pizzaStoreService.GetMaterialsByIds(request));
        }

        [HttpPost]
        public IActionResult UpdateMaterialsQuantities([FromBody] UpdateMaterialsQuantitiesRequest request)
        {
            if (request is null)
                return BadRequest();

            return Ok(_pizzaStoreService.UpdateMaterialsQuantities(request));
        }

        [HttpGet]
        public IActionResult GetStoreBudget()
            => Ok(_pizzaStoreService.GetStoreBudget());

        [HttpPost]
        public ActionResult UpdateStoreBudget([FromBody] UpdateStoreBudgetRequest request)
        {
            if (request is null)
                return BadRequest();

            var response = _pizzaStoreService.UpdateStoreBudget(request);

            return Ok(response);
        }

        [HttpPost]
        public IActionResult StoreOrderData([FromBody] StoreOrderDataRequest request)
        {
            if (request is null)
                return BadRequest();

            return Ok(_pizzaStoreService.StoreOrderData(request));
        }
    }
}