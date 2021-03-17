package com.example.restservice.service;

import com.example.restservice.dto.request.UpdatePriceRequest;
import com.example.restservice.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(long id);

    void save(Product product);

    //TODO: update status not really delete
    void deleteById(long id);

    List<Product> filterProducts(String brand, String color);

    List<Product> searchProducts(String query);

    List<Product> sortProducts(boolean name, boolean price);

    int updateProductPrice(UpdatePriceRequest updatePriceRequest);
}
