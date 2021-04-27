package com.shopme.admin.brand.service;

import com.shopme.admin.brand.BrandRepository;
import com.shopme.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    /*public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }*/

    /**
     * Update/Save user
     * @param user user information
     * @return user saved
     */
    /*public User save(User user) {
        boolean isUpdatingUser = user.getId() != null;
        if (isUpdatingUser) {
            User existingUser = userRepository.findById(user.getId()).get();
            if(user.getPassword().isEmpty()) { // not set new password then use exist password
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepository.save(user);
    }*/


    /*public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) {
            return true;
        }

        if (id == null) {
            return false;
        } else {
            return userByEmail.getId().equals(id);
        }
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Could not find any user with id: " + id);
        }
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with id: " + id);
        }

        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enable) {
        userRepository.updateEnabledStatus(id, enable);
    }*/
}
