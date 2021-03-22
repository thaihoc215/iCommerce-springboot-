package oldimplement.service;

import oldimplement.dto.CustomerActivityDto;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(long id);

    void save(Customer employee);

    void deleteById(long id);

    void logCustomerActivity(CustomerActivityDto customerActivity);
}
