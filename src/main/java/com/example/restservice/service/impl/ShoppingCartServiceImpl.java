package com.example.restservice.service.impl;

import com.example.restservice.dao.CartItemRepository;
import com.example.restservice.entity.CartItem;
import com.example.restservice.entity.Customer;
import com.example.restservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CartItemRepository cartRepo;

    @Override
    public List<CartItem> listCartItems(Customer customer) {
        return cartRepo.findByCustomer(customer);
    }
}
