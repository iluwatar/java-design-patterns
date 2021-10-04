package com.company;

public class HotpizzaBuilder implements PizzaBuilder{

    private Pizza pizza;

    HotpizzaBuilder()
    {
        this.pizza = new Pizza();
    }

    @Override
    public void buildPizzaName()
    {
        pizza.setPizzaName("HotPizza");
    }

    @Override
    public void buildPizzaPrice()
    {
        switch (pizza.getSize())
        {
            case SMALL:
                pizza.setPizzaPrice(25);
                break;
            case MEDIUM:
                pizza.setPizzaPrice(30);
                break;
            case BIG:
                pizza.setPizzaPrice(35);
        }
    }

    @Override
    public void buildPizzaIngredients()
    {
        pizza.setPizzaIngredients(Ingredients.SALAMI, Ingredients.HOT_PEPPER, Ingredients.ONION, Ingredients.CHEESE);
    }


    @Override
    public void buildPizzaSize(Sizes size)
    {
        pizza.setPizzaSize(size);
    }

    @Override
    public Pizza getPizza()
    {
        return this.pizza;
    }
}
