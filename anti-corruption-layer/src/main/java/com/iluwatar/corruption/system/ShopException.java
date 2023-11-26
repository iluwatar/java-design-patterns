package com.iluwatar.corruption.system;

public class ShopException extends Exception {
    public ShopException(String message) {
        super(message);
    }

    public static ShopException throwDupEx(String key) throws ShopException {
        throw new ShopException("The order is already placed: " + key);
    }

    public static ShopException throwIncorrectData(String lhs, String rhs) throws ShopException {
        throw new ShopException("The order is already placed but has an incorrect data:\n" +
                "Incoming order:  " + lhs + "\n" +
                "Existing order:  " + rhs);
    }

}
