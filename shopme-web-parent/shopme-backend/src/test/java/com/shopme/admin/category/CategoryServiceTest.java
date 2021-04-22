package com.shopme.admin.category;

import com.shopme.admin.category.service.CategoryService;
import com.shopme.common.entity.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)*/
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService; //inject the bean into the inject mock

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateName() {
        Integer id = null;
        String name = "Computers";
        String alias = "abc";

        Category categoryCreate = new Category(id, name, alias);

        Mockito.when(categoryRepository.findByName(name)).thenReturn(categoryCreate);
        Mockito.when(categoryRepository.findByAlias("alias")).thenReturn(null);

        String result = categoryService.checkUnique(id, name, alias);
        assertEquals(result, "DuplicateName");
    }

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateAlias() {
        Integer id = null;
        String name = "abc";
        String alias = "computers";

        Category categoryCreate = new Category(id, name, alias);

        Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
        Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(categoryCreate);

        String result = categoryService.checkUnique(id, name, alias);
        assertEquals(result, "DuplicateAlias");
    }

    @Test
    public void testCheckUniqueInNewModeReturnOk() {
        Integer id = null;
        String name = "abc";
        String alias = "abc";

        Category categoryCreate = new Category(id, name, alias);

        Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
        Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(null);

        String result = categoryService.checkUnique(id, name, alias);
        assertEquals(result, "OK");
    }
}
