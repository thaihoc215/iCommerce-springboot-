package com.example.restservice.controller;

import com.example.restservice.dto.ProductDto;
import com.example.restservice.dto.request.UpdatePriceRequest;
import com.example.restservice.entity.Product;
import com.example.restservice.service.ProductService;
import com.example.restservice.util.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDto> findAll() {
        return ProductConverter.toProductDtoList(productService.findAll());
    }

    @GetMapping("/products/{id}")
    public ProductDto findById(@PathVariable long id) {
        return ProductConverter.toProductDto(productService.findById(id));
    }

    @GetMapping("/products/filter")
    public List<ProductDto> filterProducts(@RequestParam(value = "brand", required = false) String brand,
                                           @RequestParam(value = "color", required = false) String color) {
        return ProductConverter.toProductDtoList(productService.filterProducts(brand, color));
    }

    @GetMapping("/products/search")
    public List<ProductDto> searchProducts(@RequestParam(value = "query") String query) {
        return ProductConverter.toProductDtoList(productService.searchProducts(query));
    }

    @GetMapping("/products/sort")
    public List<ProductDto> sortProducts(@RequestParam(value = "nameAsc", required = false) boolean nameAsc,
                                           @RequestParam(value = "priceAsc", required = false) boolean priceAsc) {
        return ProductConverter.toProductDtoList(productService.sortProducts(nameAsc, priceAsc));
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        product.setId(0);
        productService.save(product);
        return product;
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product product) {
        if (product == null || product.getId() == 0) {
            throw new RuntimeException("Please provide product to update");
        }

        //TODO update here
        Product productUpdate = productService.findById(product.getId());
        if (productUpdate == null) {
            throw new RuntimeException("Not found product to update");
        }
        productService.save(product);
        return product;
    }

    @DeleteMapping("/products/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new RuntimeException("Not found product to delete");
        }
        productService.deleteById(id);
        return "Delete product " + id + ": " + product.getName();
    }

    @PutMapping("/products/price")
    public int updateProductPrice(@RequestBody UpdatePriceRequest updatePriceRequest) {
        if (updatePriceRequest == null || updatePriceRequest.getProductId() == 0) {
            throw new RuntimeException("Please provide updatePriceRequest to update price");
        }
        //TODO: check exist
        return productService.updateProductPrice(updatePriceRequest);
    }
}
