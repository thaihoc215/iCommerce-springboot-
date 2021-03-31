package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final String DEFAULT_EMAIL = "thaihoc2105@gmail.com";

    @Test
    public void testCreateFirstUserWithOneRole() {
        User user = new User();
        user.setEmail("hoc2105@gmail.com");
        user.setPassword("123");
        user.setFirstName("Hoc");
        user.setLastName("Ha");
        user.addRole(entityManager.find(Role.class, 1));

        User userSaved = userRepository.save(user);
        assertThat(userSaved.getId()).isGreaterThan(0);
        assertEquals(userSaved.getFirstName(), user.getFirstName());
        assertFalse(userSaved.isEnabled());
        Assert.assertEquals(1, userSaved.getRoles().size());
        assertEquals(userSaved.getRoles().iterator().next().getName(), "Admin");
    }

    @Test
    public void testCreateFirstUserWithTwoRoles() {
        User user = new User();
        user.setEmail("hoc2roles@gmail.com");
        user.setPassword("123");
        user.setFirstName("Hoc");
        user.setLastName("Ha");
        //add role admin
        user.addRole(entityManager.find(Role.class, 1));
        //add role saleperson
        user.addRole(entityManager.find(Role.class, 2));

        User userSaved = userRepository.save(user);
        assertThat(userSaved.getId()).isGreaterThan(0);
        assertEquals(userSaved.getFirstName(), user.getFirstName());
        assertFalse(userSaved.isEnabled());
        Assert.assertEquals(2, userSaved.getRoles().size());
    }

    @Test
    public void testFindUser() {
        User existUser = userRepository.findById(1).get();
        assertEquals(existUser.getEmail(), "thaihoc2105@gmail.com");
        assertEquals(existUser.getRoles().size(), 2);
        List<String> roles = existUser.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        assertTrue(roles.contains("Admin"));
        assertTrue(roles.contains("Salesperson"));
    }

    @Test
    public void testFindAllUsers() {
        Iterable<User> allUser = userRepository.findAll();
        allUser.forEach(u -> System.out.println(u));
    }

    @Test
    public void testUpdateUserDetails() {
        User existUser = userRepository.findById(1).get();
        assertTrue(existUser.isEnabled());
        existUser.setEnabled(false);
        userRepository.save(existUser);
        assertFalse(existUser.isEnabled());

        /*Role role = new Role();
        role.setId(1);
        existUser.getRoles().remove(role);*/
    }

    @Test
    public void testUpdateUserRole() {
        User user = new User();
        user.setEmail("hocchangeRole@gmail.com");
        user.setPassword("123");
        user.setFirstName("Hoc");
        user.setLastName("Ha");
        //add role admin
        user.addRole(entityManager.find(Role.class, 1));
        User userSaved = userRepository.save(user);
        Assert.assertEquals(1, userSaved.getRoles().size());
        assertEquals(userSaved.getRoles().iterator().next().getName(), "Admin");

        //update role
        System.out.println("=======update role=========");
        Role role = new Role();
        role.setId(1);
        User userUpdate = userRepository.findById(userSaved.getId()).get();
        userUpdate.getRoles().remove(role);
        userUpdate.addRole(entityManager.find(Role.class,2));
        userRepository.save(userUpdate);

        User userAfterUpdated = userRepository.findById(userSaved.getId()).get();
        Assert.assertEquals(1, userAfterUpdated.getRoles().size());
        assertEquals(userAfterUpdated.getRoles().iterator().next().getName(), "Salesperson");
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setEmail("deleteUser@gmail.com");
        user.setPassword("123");
        user.setFirstName("Hoc");
        user.setLastName("Ha");
        User userSaved = userRepository.save(user);

        userRepository.deleteById(userSaved.getId());
        assertFalse(userRepository.findById(userSaved.getId()).isPresent());
    }

    @Test
    public void testGetUserByEmail() {
        String email = DEFAULT_EMAIL;
        User userByEmail = userRepository.getUserByEmail(email);
        Assert.assertNotNull(userByEmail);
        assertEquals(userByEmail.getId(), 1);

        email = "notfound@gmail.com";
        userByEmail = userRepository.getUserByEmail(email);
        assertNull(userByEmail);
    }

    @Test
    public void testCountUserById() {
        Long count = userRepository.countById(1);
        assertEquals(count, 1);

        count = userRepository.countById(100);
        assertEquals(count, 0);
    }

    @Test
    public void testDisableUser() {
        userRepository.updateEnabledStatus(1, false);
        User user1 = userRepository.findById(1).get();
        assertFalse(user1.isEnabled());
    }

    @Test
    public void testEnableUser() {
        userRepository.updateEnabledStatus(2, true);
        User user1 = userRepository.findById(2).get();
        assertTrue(user1.isEnabled());
    }
}
