package com.iluwatar.filter;

import com.iluwatar.filter.criteria.LowPriceCriteria;
import com.iluwatar.filter.criteria.MediumPriceCriteria;
import com.iluwatar.filter.criteria.HighPriceCriteria;
import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PriceCriteriaTest {
   
    /**
     * Tests for low price critera. Three products are added, one product is filtered out 
     * due to not meeting the criteria.
     */
    @Test
    public void testLowPrice() {
        LowPriceCriteria criteria = new LowPriceCriteria();
        Product jeans = new Product("Jeans", 1000, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 199, ProductCategory.CLOTHING, 1);
        Product socks = new Product("Socks", 100, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(socks);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        expected.add(shirt);
        expected.add(socks);
        Assert.assertEquals(expected, criteriaRes);
    }

    /**
     * Test for low price criteria. No products are added, so an empty ArrayList should be returned.
     */
    @Test
    public void testEmptyProductList() {
        LowPriceCriteria criteria = new LowPriceCriteria();
        List<Product> products = new ArrayList<>();
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();

        Assert.assertEquals(expected, criteriaRes);
    }

    /**
     * Test for low price criteria. All products should pass the filter.
     */
    @Test
    public void testAllMeetCriteria() {
        LowPriceCriteria criteria = new LowPriceCriteria();
        Product jeans = new Product("Jeans", 50, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 199, ProductCategory.CLOTHING, 1);
        Product socks = new Product("Socks", 100, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(socks);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        expected.add(jeans);
        expected.add(shirt);
        expected.add(socks);
        Assert.assertEquals(expected, criteriaRes);
    }

    /**
     * Test for low price criteria. No products meet the criteria and should be filtered out. 
     */
    @Test
    public void testNoMeetCriteria() {
        LowPriceCriteria criteria = new LowPriceCriteria();
        Product jeans = new Product("Jeans", 500, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 200, ProductCategory.CLOTHING, 1);
        Product socks = new Product("Socks", 300, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(socks);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        Assert.assertEquals(expected, criteriaRes);
    }


    /**
     * Tests for medium price critera. Three products are added, one product is filtered out 
     * due to not meeting the criteria.
     */
    @Test
    public void testMediumPrice() {
        MediumPriceCriteria criteria = new MediumPriceCriteria();
        Product jeans = new Product("Jeans", 1000, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 400, ProductCategory.CLOTHING, 1);
        Product shoes = new Product("Shoes", 600, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(shoes);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        expected.add(shirt);
        expected.add(shoes);
        Assert.assertEquals(expected, criteriaRes);
    }

    /**
     * Test for medium price criteria. No products are added, so an empty ArrayList should be returned.
     */
    @Test
    public void testEmptyProductListMediumPrice() {
        MediumPriceCriteria criteria = new MediumPriceCriteria();
        List<Product> products = new ArrayList<>();
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();

        Assert.assertEquals(expected, criteriaRes);
    }

     /**
     * Tests for medium price critera. Three products are added, all products meet the criteria.
     */
    @Test
    public void testAllMeetCriteriaMediumPrice() {
        MediumPriceCriteria criteria = new MediumPriceCriteria();
        Product jeans = new Product("Jeans", 700, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 400, ProductCategory.CLOTHING, 1);
        Product shoes = new Product("Shoes", 600, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(shoes);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        expected.add(jeans);
        expected.add(shirt);
        expected.add(shoes);
        Assert.assertEquals(expected, criteriaRes);
    }

    /**
     * Tests for medium price critera. Three products are added, no products meet the criteria and are filtered out.
     */
    @Test
    public void testNoMeetCriteriaMediumPrice() {
        MediumPriceCriteria criteria = new MediumPriceCriteria();
        Product jeans = new Product("Jeans", 1000, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 190, ProductCategory.CLOTHING, 1);
        Product shoes = new Product("Shoes", 800, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(shoes);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        Assert.assertEquals(expected, criteriaRes);
    }

    /**
     * Tests for high price critera. Three products are added, one product is filtered out 
     * due to not meeting the criteria.
     */
    @Test
    public void testHighPrice() {
        HighPriceCriteria criteria = new HighPriceCriteria();
        Product jeans = new Product("Jeans", 1200, ProductCategory.CLOTHING, 1);
        Product tuxedo = new Product("Shirt", 4000, ProductCategory.CLOTHING, 1);
        Product shoes = new Product("Shoes", 600, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(tuxedo);
        products.add(shoes);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        expected.add(jeans);
        expected.add(tuxedo);
        Assert.assertEquals(expected, criteriaRes);
    }
    
    /**
     * Test for high price criteria. No products are added, so an empty ArrayList should be returned.
     */
    @Test
    public void testEmptyProductListHighPrice() {
        HighPriceCriteria criteria = new HighPriceCriteria();
        List<Product> products = new ArrayList<>();
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();

        Assert.assertEquals(expected, criteriaRes);
    }

     /**
     * Tests for high price critera. Three products are added, all products meet the criteria.
     */
    @Test
    public void testAllMeetCriteriaHighPrice() {
        HighPriceCriteria criteria = new HighPriceCriteria();
        Product jeans = new Product("Jeans", 1200, ProductCategory.CLOTHING, 1);
        Product tuxedo = new Product("Shirt", 4000, ProductCategory.CLOTHING, 1);
        Product shoes = new Product("Shoes", 1100, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(tuxedo);
        products.add(shoes);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        expected.add(jeans);
        expected.add(tuxedo);
        expected.add(shoes);
        Assert.assertEquals(expected, criteriaRes);
    }

     /**
     * Tests for high price critera. Three products are added, no products meet the criteria and are filtered out.
     */
    @Test
    public void testNoMeetCriteriaHighPrice() {
        HighPriceCriteria criteria = new HighPriceCriteria();
        Product jeans = new Product("Jeans", 1000, ProductCategory.CLOTHING, 1);
        Product shirt = new Product("Shirt", 400, ProductCategory.CLOTHING, 1);
        Product shoes = new Product("Shoes", 600, ProductCategory.CLOTHING, 1);
        List<Product> products = new ArrayList<>();
        products.add(jeans);
        products.add(shirt);
        products.add(shoes);
        List<Product> criteriaRes = criteria.meetCriteria(products);
        List<Product> expected = new ArrayList<>();
        Assert.assertEquals(expected, criteriaRes);
    }

}
