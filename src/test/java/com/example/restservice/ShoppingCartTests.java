package com.example.restservice;

import com.example.restservice.dao.CartItemRepository;
import com.example.restservice.entity.CartItem;
import com.example.restservice.entity.Customer;
import com.example.restservice.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ShoppingCartTests {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testAddOneCartItem() {
        Product product = testEntityManager.find(Product.class, 2l);
        Customer customer = testEntityManager.find(Customer.class, 2l);

        CartItem newCartItem = new CartItem();
        newCartItem.setCustomer(customer);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(5);

        CartItem cartItem = cartRepo.save(newCartItem);
        assertTrue(cartItem.getId() > 0);
    }

    @Test
    public void testGetCartItemsByCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        List<CartItem> byCustomer = cartRepo.findByCustomer(customer);
        assertEquals(byCustomer.size(), 2);
    }
}
