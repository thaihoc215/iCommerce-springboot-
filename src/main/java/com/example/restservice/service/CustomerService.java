package com.example.restservice.service;

import com.example.restservice.dto.CustomerActivityDto;
import com.example.restservice.entity.Customer;
import com.example.restservice.entity.CustomerActivity;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(long id);

    void save(Customer employee);

    void deleteById(long id);

    void logCustomerActivity(CustomerActivityDto customerActivity);
}
