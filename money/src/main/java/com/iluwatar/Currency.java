package com.iluwatar;/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * The Currency class represents a currency type with a cent factor and a string representation.
 * It is used to define different currencies in the monetary system.
 *
 * The cent factor is used to convert currency amounts to cents for internal calculations.
 * The string representation is a human-readable identifier for the currency.
 */
public class Currency {
    private int centFactor;
    private String stringRepresentation;

    /**
     * Constructs a Currency with the specified cent factor and string representation.
     *
     * @param centFactor           The cent factor for the currency.
     * @param stringRepresentation The string representation of the currency.
     */
    public Currency(int centFactor, String stringRepresentation) {
        this.centFactor = centFactor;
        this.stringRepresentation = stringRepresentation;
    }

    /**
     * Gets the cent factor for the currency.
     *
     * @return The cent factor of the currency.
     */
    public int getCentFactor() {
        return centFactor;
    }

    /**
     * Gets the string representation of the currency.
     *
     * @return The string representation of the currency.
     */
    public String getStringRepresentation() {
        return stringRepresentation;
    }

    /**
     * Creates a new Currency instance representing the US Dollar (USD).
     *
     * @return A Currency instance for USD.
     */
    public static Currency USD() {
        return new Currency(100, "USD");
    }

    /**
     * Creates a new Currency instance representing the Euro (EUR).
     *
     * @return A Currency instance for EUR.
     */
    public static Currency EUR() {
        return new Currency(100, "EUR");
    }
}
