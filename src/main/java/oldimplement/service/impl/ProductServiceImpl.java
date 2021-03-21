package oldimplement.service.impl;

import oldimplement.dao.PriceTrackingRepository;
import oldimplement.dao.ProductRepository;
import oldimplement.dto.CustomerActivityDto;
import oldimplement.dto.request.UpdatePriceRequest;
import oldimplement.entity.PriceTracking;
import oldimplement.entity.Product;
import oldimplement.service.CustomerService;
import oldimplement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productDao;

    @Autowired
    private PriceTrackingRepository priceTrackingDao;

    @Autowired
    CustomerService customerService;

    @Override
    public List<Product> findAll() {
        //TODO: update customer ID follow request
        CustomerActivityDto customerActivityDto = new CustomerActivityDto(1, "find all", System.currentTimeMillis());
        customerService.logCustomerActivity(customerActivityDto);
        return productDao.findAll();
    }

    @Override
    public Product findById(long theId) {

        CustomerActivityDto customerActivityDto = new CustomerActivityDto(1, "find one product", System.currentTimeMillis());
        customerService.logCustomerActivity(customerActivityDto);
        Optional<Product> product = productDao.findById(theId);
        if (product.isPresent()) {
            return product.get();
        } else
            throw new RuntimeException("Did not find product");
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void deleteById(long id) {
        productDao.deleteById(id);
    }

    @Override
    public List<Product> filterProducts(String brand, String color) {
        CustomerActivityDto customerActivityDto = new CustomerActivityDto(1, "filter product", System.currentTimeMillis());
        customerService.logCustomerActivity(customerActivityDto);
        return productDao.filterProducts(brand, color);
    }

    @Override
    public List<Product> searchProducts(String query) {
        CustomerActivityDto customerActivityDto = new CustomerActivityDto(1, "search product", System.currentTimeMillis());
        customerService.logCustomerActivity(customerActivityDto);
        return productDao.searchProducts(query);
    }

    @Override
    public List<Product> sortProducts(boolean nameAsc, boolean priceAsc) {
        CustomerActivityDto customerActivityDto = new CustomerActivityDto(1, "sort product", System.currentTimeMillis());
        customerService.logCustomerActivity(customerActivityDto);
        return productDao.sortProducts(nameAsc, priceAsc);
    }

    @Override
    public int updateProductPrice(UpdatePriceRequest updatePriceRequest) {
        if (!productDao.existsById(updatePriceRequest.getProductId())) {
            return 0;
        }
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        PriceTracking priceTracking = new PriceTracking();
        priceTracking.setProductId(updatePriceRequest.getProductId());
        priceTracking.setPriceBeforeUpdate(updatePriceRequest.getNewPrice());
        priceTracking.setModifiedBy("test");
        priceTracking.setModifiedTime(updateTime);
        priceTrackingDao.save(priceTracking);

//        productDao.findById(updatePriceRequest.getProductId()).get().getPriceTrackings().add(priceTracking);
        return productDao.updateProductPrice(updatePriceRequest, updateTime);
    }
}
