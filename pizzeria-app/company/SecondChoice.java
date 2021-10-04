package com.company;
import java.util.Scanner;

public class SecondChoice extends methode {
    public String DeliveryType;
    public String DeliveryAddress;

    public SecondChoice(String message) {
        super(message);
        Scanner scannerDeliveryType = new Scanner(System.in);
        Scanner scannerDeliveryAddress = new Scanner(System.in);
        DeliveryType = scannerDeliveryType.nextLine();
        DeliveryAddress = scannerDeliveryAddress.nextLine();
        type=DeliveryType;
        address=DeliveryAddress;

    }

}
