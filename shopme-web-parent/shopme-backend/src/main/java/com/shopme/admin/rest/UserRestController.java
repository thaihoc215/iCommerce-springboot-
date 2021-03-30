package com.shopme.admin.rest;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "rest")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> listAll(Model model) {
        List<User> listUsers = userService.listAllUsers();
        return listUsers;
    }

    @PostMapping("/users/save")
    public void newUser(@RequestBody User user) {
        System.out.println(user);
        if (userService.isEmailUnique(null, user.getEmail())) {
            user.setEnabled(true);
            userService.save(user);
        } else {
            throw new IllegalArgumentException("email is using by another user");
        }
    }

    @PutMapping("/users/save")
    public void updateUser(@RequestBody User user) {
        System.out.println(user);
        if (userService.isEmailUnique(user.getId(), user.getEmail())) {
            user.setEnabled(true);
            userService.save(user);
        } else {
            throw new IllegalArgumentException("email is using by another user");
        }
    }

    @PostMapping("/users/checkEmail")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

}
