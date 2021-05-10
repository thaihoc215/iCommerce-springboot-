package com.shopme.admin.brand;

import com.shopme.admin.brand.service.BrandService;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTest {
    @MockBean
    private BrandRepository brandRepo;

    @InjectMocks
    private BrandService brandService; //inject the bean into the inject mock

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateName() {
        Integer id = null;
        String name = "Canon";

        Brand brand = new Brand(id, name, "");

        Mockito.when(brandRepo.findByName(name)).thenReturn(brand);

        String result = brandService.checkUnique(id, name);
        assertEquals(result, "DuplicateName");
    }

    @Test
    public void testCheckUniqueInNewModeReturnOk() {
        Integer id = null;
        String name = "abc";

        Brand brand = new Brand(id, name, "");

        Mockito.when(brandRepo.findByName(name)).thenReturn(null);

        String result = brandService.checkUnique(id, name);
        assertEquals(result, "OK");
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicateName() {
        Integer id = 2;
        String name = "Canon";

        Brand brand = new Brand(id, name, "");

        Mockito.when(brandRepo.findByName(name)).thenReturn(brand);

        String result = brandService.checkUnique(id, name);
        assertEquals(result, "DuplicateName");
    }

    @Test
    public void testCheckUniqueInEditModeReturnOk() {
        Integer id = null;
        String name = "abc";

        Brand brand = new Brand(id, name, "");

        Mockito.when(brandRepo.findByName(name)).thenReturn(null);

        String result = brandService.checkUnique(id, name);
        assertEquals(result, "OK");
    }
}
