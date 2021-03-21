package oldimplement.service;

import oldimplement.dto.request.UpdatePriceRequest;
import oldimplement.entity.Product;

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
