package com.example.restservice.dao;

import com.example.restservice.entity.CartItem;
import com.example.restservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    public List<CartItem> findByCustomer(Customer customer);
}
