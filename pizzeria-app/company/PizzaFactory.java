
package com.company;
public interface PizzaFactory
{


    default Pizza createPizzaVegetarian(Sizes size)
    {
        PizzaBuilder pizzaBuilder = new VegetarianBuilder();
        PizzaEngineer pizzaEngineer = new PizzaEngineer(pizzaBuilder);
        pizzaEngineer.makePizza(size);
        return pizzaEngineer.getPizza();
    }

    default Pizza createPizzaMargerite(Sizes size)
    {
        PizzaBuilder pizzaBuilder = new MargeriteBuilder();
        PizzaEngineer pizzaEngineer = new PizzaEngineer(pizzaBuilder);
        pizzaEngineer.makePizza(size);
        return pizzaEngineer.getPizza();
    }

    default  Pizza createPizzaHotpizza(Sizes size)
    {
        PizzaBuilder pizzaBuilder = new HotpizzaBuilder();
        PizzaEngineer pizzaEngineer = new PizzaEngineer(pizzaBuilder);
        pizzaEngineer.makePizza(size);
        return pizzaEngineer.getPizza();
    }

    
    String getRestaurantAddress();

}
