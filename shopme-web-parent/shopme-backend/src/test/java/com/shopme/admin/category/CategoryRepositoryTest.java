package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category("Computers1", "Computers 1", null);
        Category catSaved = categoryRepository.save(category);
        assertThat(catSaved.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory() {
        Category parent = new Category();
        parent.setId(1);
        Category subCat = new Category("Desktops1", parent);
        Category subCat2 = new Category("Laptops1", parent);
        Category subCat3 = new Category("Computer Components1", parent);
        Iterable<Category> categories = categoryRepository.saveAll(Arrays.asList(subCat, subCat2, subCat3));
        categories.forEach(i -> {
            assertThat(i.getId()).isGreaterThan(0);
            assertEquals(i.getParent().getId(), 1);
        });

    }

    @Test
    public void testGetCategory() {
        Category category = categoryRepository.findById(1).get();
        System.out.println("Parent-----");
        System.out.println(category.getName());

        System.out.println("Children-----");
        Set<Category> children = category.getChildren();
        children.forEach(c -> {
            System.out.println(c.getName());
        });

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierachicalCategories() {
        Iterable<Category> all = categoryRepository.findAll();
        for (Category category : all) {
            if (category.getParent() == null) {
                System.out.println(category.getName());
//                System.out.println("Sub category 1");
                Set<Category> children = category.getChildren();
                for (Category sub : children) {
                    System.out.println("--" + sub.getName());
                    printChildren(sub, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();
        for (Category sub : children) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(sub.getName());
            printChildren(sub, newSubLevel);
        }
    }

    @Test
    public void testListRootCategories() {
        List<Category> categories = categoryRepository.findRootCategories(Sort.by("name").ascending());
        categories.forEach(cat -> System.out.println(cat.getName()));
    }

    @Test
    public void testFindByName() {
        String name = "Computers";
        Category cat = categoryRepository.findByName(name);

        assertNotNull(cat);
        assertEquals(cat.getName(), name);
    }

    @Test
    public void testFindByAlias() {
        String alias = "electronics";
        Category cat = categoryRepository.findByAlias(alias);

        assertNotNull(cat);
        assertEquals(cat.getAlias(), alias);
    }
}
