package com.company;


public class MargeriteBuilder implements PizzaBuilder
{

    private Pizza pizza;

    MargeriteBuilder()
    {
        this.pizza = new Pizza();
    }

    @Override
    public void buildPizzaName()
    {
        pizza.setPizzaName("Margerite");
    }

    @Override
    public void buildPizzaPrice()
    {
        switch (pizza.getSize())
        {
            case SMALL:
                pizza.setPizzaPrice(22);
                break;
            case MEDIUM:
                pizza.setPizzaPrice(27);
                break;
            case BIG:
                pizza.setPizzaPrice(32);
        }
    }

    @Override
    public void buildPizzaIngredients()
    {
        pizza.setPizzaIngredients(Ingredients.CHEESE, Ingredients.SAUCE, Ingredients.HAM);
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
