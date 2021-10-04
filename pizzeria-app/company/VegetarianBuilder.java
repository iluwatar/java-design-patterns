package com.company;
public class VegetarianBuilder implements PizzaBuilder
{

    private Pizza pizza;

    VegetarianBuilder()
    {
        this.pizza = new Pizza();
    }

    @Override
    public void buildPizzaName()
    {
        pizza.setPizzaName("Vegetarian");
    }

    @Override
    public void buildPizzaPrice()
    {
        switch (pizza.getSize())
        {
            case SMALL:
                pizza.setPizzaPrice(20);
                break;
            case MEDIUM:
                pizza.setPizzaPrice(25);
                break;
            case BIG:
                pizza.setPizzaPrice(30);
        }
    }

    @Override
    public void buildPizzaIngredients()
    {
        pizza.setPizzaIngredients(Ingredients.PEPPER, Ingredients.TOMATO, Ingredients.CHEESE);
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
