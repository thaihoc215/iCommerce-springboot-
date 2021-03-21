package com.example.restservice.service;

import com.example.restservice.entity.CartItem;
import com.example.restservice.entity.Customer;

import java.util.List;

public interface ShoppingCartService {

    List<CartItem> listCartItems(Customer customer);
}
