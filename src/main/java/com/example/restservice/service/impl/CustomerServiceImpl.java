package com.example.restservice.service.impl;

import com.example.restservice.entity.Customer;
import com.example.restservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    // quick and dirty: inject user dao (use constructor injection)
    /*private CustomerRepository customerDao;

    private final CustomerActivityRepository customerActivityDao;

    @Autowired
    CustomerServiceImpl(CustomerRepository customerDao, CustomerActivityRepository customerActivityDao) {
        this.customerDao = customerDao;
        this.customerActivityDao = customerActivityDao;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findById(long id) {

        Optional<Customer> user = customerDao.findById(id);
        if (user.isPresent()) {
           return user.get();
        } else
            throw new RuntimeException("Did not find user");
    }

    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void deleteById(long id) {
        customerDao.deleteById(id);
    }

    @Async
    @Override
    public void logCustomerActivity(CustomerActivityDto customerActivityDto) {
        CustomerActivity customerActivity = new CustomerActivity();
        customerActivity.setCustomerId(customerActivityDto.getCustomerId());
        customerActivity.setAction(customerActivityDto.getAction());
        customerActivity.setActionTime(new Timestamp(customerActivityDto.getActionTime()));
        try {
            customerActivityDao.save(customerActivity);
        } catch (Exception e) {
            logger.error("Getting error when record user activity", e.getMessage());
        }

    }*/


    @Override
    public Customer getCurrentlyLoggedInCustomer(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        /*Customer customer = null;
        Object principal = authentication.getPrincipal();
        if(principal instanceof  CustomerUserDetails) {

        } else if (principal instanceof CustomOAuth2User) {
            String email = ....
            customer = customer....
        }*/
        return new Customer();
    }

}
