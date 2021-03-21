package com.example.restservice.service;

import com.example.restservice.entity.Customer;
import org.springframework.security.core.Authentication;

public interface CustomerService {
    /*List<Customer> findAll();

    Customer findById(long id);

    void save(Customer employee);

    void deleteById(long id);

    void logCustomerActivity(CustomerActivityDto customerActivity);*/

    Customer getCurrentlyLoggedInCustomer(Authentication authentication);
}
