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

import com.iluwatar.filter.criteria.Criteria;
import com.iluwatar.filter.criteria.NotCriteria;
import com.iluwatar.filter.criteria.InStockCriteria;
import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
 
/**
 * Tests for the NotCriteria class
*/
public class NotCriteriaTest {
    /**
    * Tests that the filter works correctly when all products meet the criteria.
    * Asserts that an empty list is returned.
    */
    @Test
    void testAllMeetCriteria() {
        ArrayList<Criteria> exclusionCriteria = new ArrayList<Criteria>();
        exclusionCriteria.add(new InStockCriteria());

        NotCriteria notCriteria = new NotCriteria(exclusionCriteria);

        Product laptop = new Product("Laptop", 2000, ProductCategory.ELECTRONICS, 50);
        Product charger = new Product("Charger", 30, ProductCategory.ELECTRONICS, 200);
        Product television = new Product("TV", 600, ProductCategory.ELECTRONICS, 12);

        ArrayList<Product> products = new ArrayList<>();
        products.add(laptop);
        products.add(charger);
        products.add(television);
        List<Product> remainingProducts = notCriteria.meetCriteria(products);

        List<Product> validProducts = new ArrayList<>();

        Assert.assertEquals(remainingProducts, validProducts);
    }

    /**
     * Tests that the filter works correctly when some products meet the criteria.
    * Asserts that of the three products added, the two out of stock are returned.
    */
    @Test
    void testSomeMeetCriteria() {
        ArrayList<Criteria> exclusionCriteria = new ArrayList<Criteria>();
        exclusionCriteria.add(new InStockCriteria());

        NotCriteria notCriteria = new NotCriteria(exclusionCriteria);

        Product laptop = new Product("Laptop", 2000, ProductCategory.ELECTRONICS, 50);
        Product charger = new Product("Charger", 30, ProductCategory.ELECTRONICS, 0);
        Product television = new Product("TV", 600, ProductCategory.ELECTRONICS, 0);

        ArrayList<Product> products = new ArrayList<>();
        products.add(laptop);
        products.add(charger);
        products.add(television);
        List<Product> remainingProducts = notCriteria.meetCriteria(products);

        List<Product> validProducts = new ArrayList<>();
        validProducts.add(charger);
        validProducts.add(television);

        Assert.assertEquals(remainingProducts, validProducts);
    }

    /**
     * Tests that the filter works correctly when no products meet the criteria.
    * Asserts that all products are returned.
    */
    @Test
    void testNoneMeetCriteria() {
        ArrayList<Criteria> exclusionCriteria = new ArrayList<Criteria>();
        exclusionCriteria.add(new InStockCriteria());

        NotCriteria notCriteria = new NotCriteria(exclusionCriteria);

        Product laptop = new Product("Laptop", 2000, ProductCategory.ELECTRONICS, 0);
        Product charger = new Product("Charger", 30, ProductCategory.ELECTRONICS, 0);
        Product television = new Product("TV", 600, ProductCategory.ELECTRONICS, 0);

        ArrayList<Product> products = new ArrayList<>();
        products.add(laptop);
        products.add(charger);
        products.add(television);
        List<Product> remainingProducts = notCriteria.meetCriteria(products);

        List<Product> validProducts = new ArrayList<>();
        validProducts.add(laptop);
        validProducts.add(charger);
        validProducts.add(television);

        Assert.assertEquals(remainingProducts, validProducts);
    }
}
