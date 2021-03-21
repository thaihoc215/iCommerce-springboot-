package com.example.restservice.controller;

import com.example.restservice.entity.CartItem;
import com.example.restservice.entity.Customer;
import com.example.restservice.service.CustomerService;
import com.example.restservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("cart")
    public List<CartItem> showShoppinrgCart(Model model, @AuthenticationPrincipal Authentication authentication) {
        Customer customer = customerService.getCurrentlyLoggedInCustomer(authentication);
        return cartService.listCartItems(customer);
    }
}
