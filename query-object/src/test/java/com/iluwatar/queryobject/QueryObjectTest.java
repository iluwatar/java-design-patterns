package com.iluwatar.queryobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class QueryObjectTest {
    private Repository<Customer> repos;
    @BeforeEach
    public void setUp(){
        repos = new Repository<>(new Dataset<>(
                Arrays.asList(
                        new Customer(
                                "Customer 1",
                                Arrays.asList(
                                        new CustomerOrder(
                                                Collections.singletonList(
                                                        new CustomerOrderDetail(1, 100)
                                                )
                                        ),
                                        new CustomerOrder(
                                                Arrays.asList(
                                                        new CustomerOrderDetail(3, 10),
                                                        new CustomerOrderDetail(4, 20)
                                                )
                                        )
                                )
                        ),
                        new Customer(
                                "Customer 2",
                                Arrays.asList(
                                        new CustomerOrder(
                                                Arrays.asList(
                                                        new CustomerOrderDetail(1, 10),
                                                        new CustomerOrderDetail(1, 10)
                                                )
                                        ),
                                        new CustomerOrder(
                                                Arrays.asList(
                                                        new CustomerOrderDetail(1, 1),
                                                        new CustomerOrderDetail(1, 5)
                                                )
                                        ),
                                        new CustomerOrder(
                                                Arrays.asList(
                                                        new CustomerOrderDetail(5, 10),
                                                        new CustomerOrderDetail(2, 20)
                                                )
                                        )
                                )
                        )
                )
        ).entities);
    }

    @Test
    public void testWithSingleCondition(){
        var condition = new CustomersWithOrdersAmountMoreThan(2);
        var result = repos.query(condition);
        assertEquals(1, result.size());
    }

    @Test
    public void testWithDuplicatedCondition(){
        var condition = new CustomersWithOrdersAmountMoreThan(2);
        var result = repos.query(condition, condition);
        assertEquals(1, result.size());
    }

    @Test
    public void testWithMultipleConditions(){
        var condition1 = new CustomersWithOrdersAmountMoreThan(2);
        var condition2 = new CustomerSalesWithPurchaseMoreThan(200);
        var result = repos.query(condition1, condition2);
        assertEquals(0, result.size());
    }

    @Test
    public void testWithMultipleConditions2(){
        var condition1 = new CustomersWithOrdersAmountMoreThan(2);
        var condition2 = new CustomerSalesWithPurchaseMoreThan(100);
        var result = repos.query(condition1, condition2);
        assertEquals(1, result.size());
    }


}
