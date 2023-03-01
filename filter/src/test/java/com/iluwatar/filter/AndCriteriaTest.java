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
import com.iluwatar.filter.criteria.InStockCriteria;
import com.iluwatar.filter.criteria.CategoryCriteria;
import com.iluwatar.filter.criteria.AndCriteria;

import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for And criteria.
 */
public class AndCriteriaTest {

    /**
     * Tests that the filter works correctly when all products meet all the criteria.
     * Asserts that no products are filtered out.
     */
    @Test
    void testAllItemsMeetCriteria(){

        Criteria inStockCriteria = new InStockCriteria();
        Criteria clothingCriteria = new CategoryCriteria(ProductCategory.CLOTHING);

        List<Criteria> criteria = new ArrayList();
        criteria.add(inStockCriteria);
        criteria.add(clothingCriteria);

        AndCriteria andCriteria = new AndCriteria(criteria);

        Product skirt = new Product("skirt", 4000, ProductCategory.CLOTHING, 5);
        Product jeans = new Product("jeans", 4000, ProductCategory.CLOTHING, 5);
        Product shirt = new Product("shirt", 500, ProductCategory.CLOTHING, 10);

        ArrayList<Product> products = new ArrayList<>();
        products.add(skirt);
        products.add(jeans);
        products.add(shirt);

        List<Product> criteriaRes = andCriteria.meetCriteria(products);
        Assert.assertEquals(criteriaRes, products);
    }

    /**
     * Tests that the filter works correctly when some products meet all the criteria.
     * Asserts that the two products that does not meet all criteria are filtered out.
     */
    @Test
    void testSomeItemsMeetCriteria(){

        Criteria inStockCriteria = new InStockCriteria();
        Criteria clothingCriteria = new CategoryCriteria(ProductCategory.CLOTHING);

        List<Criteria> criteria = new ArrayList();
        criteria.add(inStockCriteria);
        criteria.add(clothingCriteria);
        

        AndCriteria andCriteria = new AndCriteria(criteria);

        Product skirt = new Product("skirt", 4000, ProductCategory.CLOTHING, 5);
        Product hat = new Product("hat", 4000, ProductCategory.CLOTHING, 0);
        Product jeans = new Product("jeans", 4000, ProductCategory.CLOTHING, 5);
        Product shirt = new Product("shirt", 500, ProductCategory.CLOTHING, 10);
        Product computer = new Product("computer", 500, ProductCategory.ELECTRONICS, 100);


        ArrayList<Product> products = new ArrayList<>();
        products.add(skirt);
        products.add(hat);
        products.add(jeans);
        products.add(shirt);
        products.add(computer);

        List<Product> productsMetCriteria = new ArrayList<>();
        productsMetCriteria.add(skirt);
        productsMetCriteria.add(jeans);
        productsMetCriteria.add(shirt);

        List<Product> empty = new ArrayList<>();

        List<Product> criteriaRes = andCriteria.meetCriteria(products);
        Assert.assertEquals(criteriaRes, productsMetCriteria);
    }

    /**
     * Tests that the filter works correctly when no products meet all the criteria.
     * Asserts that the all products are filtered out.
     */
    @Test
    void testNoItemsMeetCriteria(){

        Criteria inStockCriteria = new InStockCriteria();
        Criteria electronicsCriteria = new CategoryCriteria(ProductCategory.ELECTRONICS);

        List<Criteria> criteria = new ArrayList();
        criteria.add(inStockCriteria);
        criteria.add(electronicsCriteria);
        

        AndCriteria andCriteria = new AndCriteria(criteria);

        Product jeans = new Product("jeans", 4000, ProductCategory.CLOTHING, 5);
        Product shirt = new Product("shirt", 500, ProductCategory.CLOTHING, 10);
        Product computer = new Product("computer", 500, ProductCategory.ELECTRONICS, 0);

        ArrayList<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(computer);

        List<Product> empty = new ArrayList<>();

        List<Product> criteriaRes = andCriteria.meetCriteria(products);
        Assert.assertEquals(criteriaRes, empty);
    }
}
