package com.company;
public interface PizzaBuilder
{

    void buildPizzaName();
    void buildPizzaPrice();
    void buildPizzaIngredients();
    void buildPizzaSize(Sizes size);

    Pizza getPizza();

}
