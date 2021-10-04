package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pizza implements PizzaPlan,observable {
    private List<Observer> observerList = new ArrayList<>();

    private String name;
    private double price;
    private List<Ingredients> ingredients = new ArrayList<>();
    private Sizes size;
    private String status;

    public void setPizzaName(String name) {
        this.name = name;
    }

    public void setPizzaIngredients(Ingredients... ingredients) {
        this.ingredients.addAll(Arrays.asList(ingredients));
    }

    public void setPizzaSize(Sizes size) {
        this.size = size;
    }

    public void setPizzaPrice(double price) {
        this.price = price;
    }


    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }

    Sizes getSize() {
        return size;
    }

    @Override
    public String toString() {
        System.out.println("Ingredients :");
        for (Ingredients i : ingredients) {
           System.out.println(i.name().toLowerCase().replace("_", " "));
        }
      return "Name: "+" "+this.name+"Size: "+" "+this.size+"Price: "+" "+this.price;


    }

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    void setStatus(boolean available) {
        status = this.getName() + (available ? " is available" : " is not available");
        System.out.println(status);
        notifyAllObservers();
    }


    @Override
    public void notifyAllObservers() {
        for (Observer observer : observerList) {
            observer.update(status);
        }
    }
}