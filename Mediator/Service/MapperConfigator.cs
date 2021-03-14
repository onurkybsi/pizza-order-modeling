using AutoMapper;
using Business = PizzaStore.Model.Business;
using Entity = PizzaStore.Model.Entity;

namespace PizzaStore.Service
{
    public static class MapperConfigurator
    {
        private static Mapper Mapper = new Mapper(new MapperConfiguration(
            cfg =>
            {
                cfg.CreateMap<Entity.MenuItem, Business.MenuItem>()
                .ForMember(d => d.Type, opt => opt.MapFrom(u => u.Type == Entity.MenuItemType.Pizza ? "PIZZA" : "DRINK"));

                cfg.CreateMap<Entity.Material, Business.Material>()
                .ForMember(d => d.Type, opt => opt.MapFrom(u => u.Type == Entity.MaterialType.Pizza ? "PIZZA" : "DRINK"));
            }
        ));

        public static TDest MapTo<TDest>(this object src)
        {
            return (TDest)Mapper.Map<TDest>(src);
        }
    }
}