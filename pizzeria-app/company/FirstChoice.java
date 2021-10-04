package com.company;



import java.util.Scanner;

public class FirstChoice extends methode {
    public String DeliveryType;
    public String DeliveryAddress;

    public FirstChoice(String message) {
        super(message);
        Scanner scannerDeliveryType = new Scanner(System.in);
        Scanner scannerDeliveryAddress = new Scanner(System.in);
        DeliveryType = scannerDeliveryType.nextLine();
        DeliveryAddress = scannerDeliveryAddress.nextLine();
        type=DeliveryType;
        address=DeliveryAddress;

    }


}