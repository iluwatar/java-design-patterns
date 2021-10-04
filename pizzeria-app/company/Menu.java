package com.company;


import java.util.Scanner;

public class Menu
{

    private int option;
    private Order order = new Order();
    private Scanner scannerOption = new Scanner(System.in);

    public void showMenu()
    {
        order.selectRestaurant();

        do
        {
            switch (option)
            {
                case 0:
                {
                    System.out.println("-----------------------------");
                    System.out.println("Welcome in Pizzeria!");
                    System.out.println("-----------------------------");
                    System.out.println("[1] Add pizza");
                    System.out.println("[2] Show order");
                    System.out.println("[3] Make an order");
                    option = scannerOption.nextInt();
                    break;
                }
                case 1:
                {
                    order.selectPizza();
                    order.setTotalPrice(order.sumOrder());
                    option = 0;
                    break;
                }
                case 2:
                {
                    System.out.println();
                    order.getPizzaList().forEach(x -> System.out.print(x + "\n"));
                    System.out.println("-----------------------------------");
                    System.out.println("The total value of the order: " + order.getTotalPrice());
                    option = 0;
                    break;
                }
                case 3:
                {
                    order.selectDelivery();
                    System.out.println("Your order has been taken to processed");
                    System.out.println("The chosen method of receipt:  " + order.getDeliveryMethod());
                    System.out.println("To pay: " + order.getTotalPrice());
                    System.out.println("Thank you for using the services of our Pizzeria");
                    option = 4;
                    break;
                }
            }
        } while (option != 4);

        scannerOption.close();
    }

}

