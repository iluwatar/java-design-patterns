package com.iluwatar.activerecord;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

    private Long id;
    private String customerNumber;
    private String firstName;
    private String lastName;
    private List<Order> orders;

    public Customer findById(Long id) {
        return new Customer();
    }

    public Customer findByNumber(String customerNumber) {
        return new Customer();
    }

    public List<Customer> findAll() {
        return List.of();
    }

    public void save(Customer customer) {

    }

}
