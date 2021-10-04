package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Order
{
    public Order(){ }

    private List<Pizza> pizzaList = new ArrayList<>();
    private double totalPrice;
    private String deliveryMethod;
    private String deliveryAddress;
    private PizzaFactory pizzaFactory;

    List<Pizza> getPizzaList() {
        return pizzaList;
    }

    double getTotalPrice() {
        return totalPrice;
    }

    void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    String getDeliveryMethod() {
        return deliveryMethod;
    }

    double sumOrder()
    {
        double sum = 0;
        for (Pizza pizza : pizzaList)
        {
            sum += pizza.getPrice();
        }
        return sum;
    }

    void selectDelivery()
    {
        int deliveryType;
        Scanner scannerDeliveryType = new Scanner(System.in);
        Scanner scannerDeliveryAddress = new Scanner(System.in);
        do
        {
            System.out.println("Choose the delivery method:");
            System.out.println("[1] Delivery to home");
            System.out.println("[2] get it in person");
            deliveryType = scannerDeliveryType.nextInt();
        } while (deliveryType != 1 && deliveryType != 2);
        if (deliveryType == 1)
        {
            System.out.println("Enter address:");
            deliveryAddress = scannerDeliveryAddress.nextLine();
            System.out.println("You have chosen delivery to home so there's an additional fee");
            totalPrice += 5;
            deliveryMethod = "Delivery to home";
        }
        else {
            System.out.println("You have chosen collection in person");
            System.out.println("Pizza to pick up in " + deliveryAddress);
            deliveryMethod = "Collection in person";
        }
        scannerDeliveryType.close();
        scannerDeliveryAddress.close();
    }

    void selectRestaurant()
    {
        int restaurantType;
        Scanner scannerRestaurantType = new Scanner(System.in);
        do
        {
            System.out.println("Choose a restaurant:");
            System.out.println("[1] Pacha");
            System.out.println("[2] Rouba");
            restaurantType = scannerRestaurantType.nextInt();
        } while (restaurantType != 1 && restaurantType != 2);
        if (restaurantType == 1)
        {
            pizzaFactory = new PizzaFactoryPacha();
            deliveryAddress = pizzaFactory.getRestaurantAddress();
        }
        else {
            pizzaFactory = new PizzaFactoryRouba();
            deliveryAddress = pizzaFactory.getRestaurantAddress();
        }
    }

    void selectPizza()
    {
        Pizza pizza = null;
        String option;
        int pizzaSize;
        int pizzaType;
        Scanner scannerPizzaSize = new Scanner(System.in);
        Scanner scannerPizzaType = new Scanner(System.in);
        Scanner scannerOption = new Scanner(System.in);
        Sizes size = null;
        do
        {
            System.out.println("Choose type of pizza");
            System.out.println("[1] Vegetarian");
            System.out.println("[2] HotPizza");
            System.out.println("[3] Margerite");
            pizzaType = scannerPizzaType.nextInt();
            System.out.println();
        } while (pizzaType < 1 && pizzaType > 3);
        do
        {
            System.out.println("Choose size of pizza");
            System.out.println("[1] Small");
            System.out.println("[2] Medium");
            System.out.println("[3] Big");
            pizzaSize = scannerPizzaSize.nextInt();
            System.out.println();
            if (pizzaSize == 1)
            {
                size = Sizes.SMALL;
            }
            else if (pizzaSize == 2)
            {
                size = Sizes.MEDIUM;
            }
            else if (pizzaSize == 3)
            {
                size = Sizes.BIG;
            }
        } while (pizzaSize < 1 && pizzaSize > 3);
        if (pizzaType == 1)
        {
            pizza = pizzaFactory.createPizzaVegetarian(size);
            PizzaDeliveryDriver driver1 = new PizzaDeliveryDriver("first driver");
            PizzaDeliveryDriver driver2= new PizzaDeliveryDriver("second driver");
            pizza.addObserver(driver1);
            pizza.addObserver(driver2);

            for(int i=1; i<3; i++) {

                boolean avaiable = i%2 == 0;
                pizza.setStatus(avaiable);

                if (i == 1) {
                    pizza.removeObserver(driver1);
                }

            }}
        else if (pizzaType == 2)
        {
            pizza = pizzaFactory.createPizzaHotpizza(size);
        }
        else if (pizzaType == 3)
        {
            pizza = pizzaFactory.createPizzaMargerite(size);
        }
        System.out.println(pizza);
        System.out.println("Do you want add pizza to your order? [Y/N]");
        option = scannerOption.nextLine();
        System.out.println();
        if (option.equalsIgnoreCase("Y"))
        {
            pizzaList.add(pizza);
            System.out.println("Pizza has been added to order!");
            System.out.println();
        }
        else if (option.equalsIgnoreCase("N"))
        {
            System.out.println("Any pizza hasn't been added!");
            System.out.println();
        }
    }

}
