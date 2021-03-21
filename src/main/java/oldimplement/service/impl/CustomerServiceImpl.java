package oldimplement.service.impl;

import oldimplement.dao.CustomerActivityRepository;
import oldimplement.dao.CustomerRepository;
import oldimplement.dto.CustomerActivityDto;
import oldimplement.entity.Customer;
import oldimplement.entity.CustomerActivity;
import oldimplement.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    // quick and dirty: inject user dao (use constructor injection)
    private CustomerRepository customerDao;

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

    }


}
