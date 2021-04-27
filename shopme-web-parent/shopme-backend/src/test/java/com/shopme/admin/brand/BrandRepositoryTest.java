package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateBrand() {
        Brand brand = new Brand("brand 1", "default.png");
        brand.addCategory(entityManager.find(Category.class, 1));
        brand.addCategory(entityManager.find(Category.class, 2));
        Brand brandSaved = brandRepo.save(brand);

        assertThat(brandSaved.getId()).isGreaterThan(0);

    }

    @Test
    public void testGetBrand() {
        Brand brand = new Brand("brand 2", "default.png");
        brand.addCategory(entityManager.find(Category.class, 1));
        brand.addCategory(entityManager.find(Category.class, 2));
        Brand brandSaved = brandRepo.save(brand);

        assertThat(brandSaved.getId()).isGreaterThan(0);

        Brand brand1 = brandRepo.findById(brandSaved.getId()).get();
        assertEquals(brand1.getName(), brand.getName());
        assertEquals(brand1.getCategories().size(), 2);
    }

    @Test
    public void testUpdateBrand() {
        Brand brand = new Brand("brand 3", "default.png");
        Brand brandSaved = brandRepo.save(brand);

        assertThat(brandSaved.getId()).isGreaterThan(0);

        Brand brand3 = brandRepo.findById(brandSaved.getId()).get();
        assertEquals(brand3.getName(), brand.getName());
        brand3.setName("brand update");
        brand3.setLogo("abc.png");
        Brand brandUpdated = brandRepo.save(brand3);

        assertEquals(brandUpdated.getName(), "brand update");
        assertEquals(brandUpdated.getLogo(), "abc.png");
    }

    @Test
    public void testDeleteBrand() {
        Brand brand = new Brand("brand 4", "default.png");
        Brand brandSaved = brandRepo.save(brand);
        assertTrue(brandRepo.findById(brandSaved.getId()).isPresent());

        brandRepo.deleteById(brandSaved.getId());
        assertFalse(brandRepo.findById(brandSaved.getId()).isPresent());
    }
}
