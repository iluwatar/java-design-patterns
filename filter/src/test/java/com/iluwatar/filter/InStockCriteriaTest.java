/*
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

package com.iluwatar.filter;

import com.iluwatar.filter.criteria.InStockCriteria;
import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for in stock criteria.
 */
public class InStockCriteriaTest {

    /**
     * Tests that the filter works correctly.
     * Asserts that of the three products added, the one that is not in stock is filtered out
     */
    @Test
    void testSomeMeetsCriteria(){
        InStockCriteria criteria = new InStockCriteria();
        Product computer = new Product("Computer", 4000, ProductCategory.ELECTRONICS, 5);
        Product phone = new Product("Smartphone", 500, ProductCategory.ELECTRONICS, 0);
        Product headphones = new Product("Headphones", 100, ProductCategory.ELECTRONICS, 1);
        ArrayList<Product> products = new ArrayList<>();
        products.add(computer);
        products.add(phone);
        products.add(headphones);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> productsInStock = new ArrayList<>();
        productsInStock.add(computer);
        productsInStock.add(headphones);
        Assert.assertEquals(criteriaRes, productsInStock);
    }

    /**
     * Tests that the filter works correctly when no products meets the criteria.
     * Asserts that all products are filtered out.
     */
    @Test
    void testNoMeetsCriteria(){
        InStockCriteria criteria = new InStockCriteria();
        Product computer = new Product("Computer", 4000, ProductCategory.ELECTRONICS, 0);
        Product phone = new Product("Smartphone", 500, ProductCategory.ELECTRONICS, 0);
        Product headphones = new Product("Headphones", 100, ProductCategory.ELECTRONICS, 0);
        ArrayList<Product> products = new ArrayList<>();
        products.add(computer);
        products.add(phone);
        products.add(headphones);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> productsInStock = new ArrayList<>();
        Assert.assertEquals(criteriaRes, productsInStock);
    }

    /**
     * Tests that the filter works correctly when all products meets the criteria.
     * Asserts that no products are filtered out.
     */
    @Test
    void testAllMeetsCriteria(){
        InStockCriteria criteria = new InStockCriteria();
        Product computer = new Product("Computer", 4000, ProductCategory.ELECTRONICS, 100);
        Product phone = new Product("Smartphone", 500, ProductCategory.ELECTRONICS, 6);
        Product headphones = new Product("Headphones", 100, ProductCategory.ELECTRONICS, 9);
        ArrayList<Product> products = new ArrayList<>();
        products.add(computer);
        products.add(phone);
        products.add(headphones);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> productsInStock = new ArrayList<>();
        productsInStock.add(computer);
        productsInStock.add(phone);
        productsInStock.add(headphones);
        Assert.assertEquals(criteriaRes, productsInStock);
    }
}
