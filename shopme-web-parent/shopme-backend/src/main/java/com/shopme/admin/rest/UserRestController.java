package com.shopme.admin.rest;

import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void saveUser(@RequestBody User user) {
        System.out.println(user);
        userService.save(user);
    }

}
