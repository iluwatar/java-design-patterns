package com.company;

class PizzaEngineer
{

    private PizzaBuilder pizzaBuilder;

    PizzaEngineer(PizzaBuilder pizzaBuilder)
    {
        this.pizzaBuilder = pizzaBuilder;
    }

    Pizza getPizza()
    {
        return this.pizzaBuilder.getPizza();
    }

    void makePizza(Sizes size)
    {
        this.pizzaBuilder.buildPizzaName();
        this.pizzaBuilder.buildPizzaIngredients();
        this.pizzaBuilder.buildPizzaSize(size);
        this.pizzaBuilder.buildPizzaPrice();
    }

}
