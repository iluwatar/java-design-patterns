package com.company;
public interface PizzaPlan
{

    void setPizzaName(String name);
    void setPizzaIngredients(Ingredients... ingredients);
    void setPizzaPrice(double price);
    void setPizzaSize(Sizes size);

}
