using Microsoft.AspNetCore.Mvc;

namespace PizzaStore.Controller
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class HealthController : ControllerBase
    {
        public IActionResult CheckHealth()
            => Ok();
    }
}