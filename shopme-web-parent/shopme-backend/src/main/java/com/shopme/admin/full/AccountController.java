package com.shopme.admin.full;

import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.service.UserService;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser, Model model) {
        String userEmail = loggedUser.getUsername();
        User user = userService.getByEmail(userEmail);
        model.addAttribute("user", user);

        return "account_form";
    }
}
