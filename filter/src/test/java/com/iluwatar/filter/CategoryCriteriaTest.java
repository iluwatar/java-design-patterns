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
import com.iluwatar.filter.criteria.CategoryCriteria;
import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains tests for category criteria.
 */
public class CategoryCriteriaTest {

    /**
     * Test when some items meets the criteria.
     * Asserts that the resulting arraylist contains the one item that meets criteria.
     */
    @Test
    void testSomeMeetCriteria(){
        Criteria criteria = new CategoryCriteria(ProductCategory.CLOTHING);

        Product computer = new Product("computer", 4000, ProductCategory.ELECTRONICS, 50);
        Product tshirt = new Product("t-shirt", 200, ProductCategory.CLOTHING, 0);
        Product headphones = new Product("headphones", 400, ProductCategory.ELECTRONICS, 4);

        ArrayList<Product> products = new ArrayList<>();
        products.add(computer);
        products.add(tshirt);
        products.add(headphones);

        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> clothingProducts = new ArrayList<>();
        clothingProducts.add(tshirt);
        Assert.assertEquals(criteriaRes, clothingProducts);
    }

    /**
     * Test when no items meets the criteria.
     * Asserts that the resulting arraylist is empty.
     */
    @Test
    void testNoneMeetsCriteria(){
        Criteria criteria = new CategoryCriteria(ProductCategory.CLOTHING);
        Product computer = new Product("computer", 4000, ProductCategory.ELECTRONICS, 50);
        Product headphones = new Product("headphones", 400, ProductCategory.ELECTRONICS, 4);
        ArrayList<Product> products = new ArrayList<>();
        products.add(computer);
        products.add(headphones);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> clothingProducts = new ArrayList<>();
        Assert.assertEquals(criteriaRes, clothingProducts);
    }

    /**
     * Test when all items meets the criteria.
     * Asserts that the resulting arraylist is the same as the parameter list.
     */
    @Test
    void testAllMeetsCriteria(){
        Criteria criteria = new CategoryCriteria(ProductCategory.CLOTHING);
        Product jeans = new Product("jeans", 4000, ProductCategory.CLOTHING, 50);
        Product tshirt = new Product("t-shirt", 200, ProductCategory.CLOTHING, 0);
        ArrayList<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(tshirt);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        Assert.assertEquals(criteriaRes, products);
    }
}
