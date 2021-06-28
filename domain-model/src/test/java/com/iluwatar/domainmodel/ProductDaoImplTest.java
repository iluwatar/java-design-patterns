/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.domainmodel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoImplTest {

    public static final String INSERT_PRODUCT_SQL =
            "insert into PRODUCTS values('product', 100, DATE '2021-06-27')";
    public static final String SELECT_PRODUCTS_SQL =
            "select name, price, expiration_date from PRODUCTS";

    private DataSource dataSource;
    private ProductDao productDao;
    private Product product;

    @BeforeEach
    void setUp() throws SQLException {
        // create schema
        dataSource = TestUtils.createDataSource();

        TestUtils.deleteSchema(dataSource);
        TestUtils.createSchema(dataSource);

        // setup objects
        productDao = new ProductDaoImpl(dataSource);

        product =
                Product.builder()
                        .name("product")
                        .price(100.0)
                        .expirationDate(LocalDate.parse("2021-06-27"))
                        .productDao(productDao)
                        .build();
    }

    @AfterEach
    void tearDown() throws SQLException {
        TestUtils.deleteSchema(dataSource);
    }

    @Test
    void findByNameTest() throws SQLException {
        var product = productDao.findByName("product");

        assertTrue(product.isEmpty());

        TestUtils.executeSQL(INSERT_PRODUCT_SQL, dataSource);

        product = productDao.findByName("product");

        assertTrue(product.isPresent());
        assertEquals(product.get().getName(), "product");
        assertEquals(product.get().getPrice(), 100);
        assertEquals(product.get().getExpirationDate(), LocalDate.parse("2021-06-27"));
    }

    @Test
    void saveTest() throws SQLException {

        productDao.save(product);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_PRODUCTS_SQL)) {

            assertTrue(rs.next());
            assertEquals(rs.getString("name"), product.getName());
            assertEquals(rs.getInt("price"), product.getPrice());
            assertEquals(rs.getDate("expiration_date").toLocalDate(), product.getExpirationDate());
        }

        assertThrows(SQLException.class, () -> productDao.save(product));
    }

    @Test
    void updateTest() throws SQLException {
        TestUtils.executeSQL(INSERT_PRODUCT_SQL, dataSource);

        product.setPrice(99.0);

        productDao.update(product);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_PRODUCTS_SQL)) {

            assertTrue(rs.next());
            assertEquals(rs.getString("name"), product.getName());
            assertEquals(rs.getDouble("price"), product.getPrice());
            assertEquals(rs.getDate("expiration_date").toLocalDate(), product.getExpirationDate());
        }
    }
}
