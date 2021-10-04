
package com.company;

import java.util.Scanner;

public class DeliveryToHome implements deliveryStrategy {
    public String deliveryAddress;
    public double totalPrice;
    public String deliveryMethod;
    int deliveryType;
    Scanner scannerDeliveryType = new Scanner(System.in);
    Scanner scannerDeliveryAddress = new Scanner(System.in);
    @Override
    public void dologic() {
        System.out.println("Enter address:");
        deliveryAddress = scannerDeliveryAddress.nextLine();
        System.out.println("You have chosen delivery to home for an additional fee");
        totalPrice += 5;
        deliveryMethod = "Delivery to home";

    }
}
