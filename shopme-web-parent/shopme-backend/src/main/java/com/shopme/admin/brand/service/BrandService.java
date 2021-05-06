package com.shopme.admin.brand.service;

import com.shopme.admin.brand.BrandRepository;
import com.shopme.admin.exception.BrandNotFoundException;
import com.shopme.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BrandService {

    public static final int BRAND_PER_PAGE = 4;

    @Autowired
    private BrandRepository brandRepo;

    public List<Brand> listAllBrands() {
        return (List<Brand>) brandRepo.findAll();
//        return (List<User>) userRepository.findAll(Sort.by("firstName").ascending());
    }

    public Page<Brand> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, BRAND_PER_PAGE, sort);
        if (keyword != null) {
            return brandRepo.findAll(keyword, pageable);
        }
        return brandRepo.findAll(pageable);
    }

    /**
     * Update/ Add brand
     * @param brand
     * @return
     */
    public Brand save(Brand brand) {
        return brandRepo.save(brand);
    }

    public Brand getBrandById(Integer id) throws BrandNotFoundException {
        try {
            return brandRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new BrandNotFoundException("Could not find any user with id: " + id);
        }
    }


    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);

        Brand brand = brandRepo.findByName(name);

        if (isCreatingNew) {
            if (brand != null) {
                return "DuplicateName";
            }
        } else {
            if (brand != null && !brand.getId().equals(id)) {
                return "DuplicateName";
            }
        }
        return "OK";
    }

    public void deleteBrand(Integer id) throws BrandNotFoundException {
        Long countById = brandRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new BrandNotFoundException("Could not find any category with id: " + id);
        }
        brandRepo.deleteById(id);
    }
}
