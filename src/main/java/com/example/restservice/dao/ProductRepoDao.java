package com.example.restservice.dao;

import com.example.restservice.dto.request.UpdatePriceRequest;
import com.example.restservice.entity.Product;

import java.sql.Timestamp;
import java.util.List;

public interface ProductRepoDao {

    /*List<Product> findAll();

    Product findById(long id);

    void save(Product product);

    void deleteById(long id);
    */

    List<Product> filterProducts(String brand, String color);

    List<Product> searchProducts(String query);

    List<Product> sortProducts(boolean nameAsc, boolean priceAsc);

    int updateProductPrice(UpdatePriceRequest updatePriceRequest, Timestamp updateTime);
}
