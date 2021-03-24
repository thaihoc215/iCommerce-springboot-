package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //-> for test with real data base
@Rollback(value = false) //to make sure after test, data will comit (with real database)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role role = new Role("Test", "manage everything");
        Role saveRole = roleRepository.save(role);
        assertThat(saveRole.getId()).isGreaterThan(0);
        assertEquals(saveRole.getDescription(), role.getDescription());

    }
}
