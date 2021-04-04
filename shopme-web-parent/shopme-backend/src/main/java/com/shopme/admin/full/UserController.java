package com.shopme.admin.full;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.util.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listFirstPage(Model model) {
        return listByPage(1, model);
    }

    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<User> page = userService.listByPage(pageNum);
        List<User> listUsers = page.getContent();
        long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("listUsers", listUsers);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());

        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = userService.listRoles();

        User user = new User();
        user.setEnabled(true);

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        /*System.out.println(user);
        System.out.println(multipartFile.getOriginalFilename());*/
        if (!multipartFile.isEmpty()) {
            String fileNameSelected = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileNameSelected);
            Integer userId = userService.save(user).getId();

            String uploadDir = "user-photos/" + userId;
            FileUploadUtil.cleanDirectory(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileNameSelected, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()){
                user.setPhotos(null);
            }
            userService.save(user);
        }

        redirectAttributes.addFlashAttribute("message", "The user has been saved " +
                "successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            User userById = userService.getUserById(id);
            model.addAttribute("user", userById);
            model.addAttribute("pageTitle", "Edit user id: " + id);

            List<Role> listRoles = userService.listRoles();
            model.addAttribute("listRoles", listRoles);

            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "The user ID: " + id +
                    " has been deleted successfully");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean status,
                                 RedirectAttributes redirectAttributes) {
        userService.updateUserEnabledStatus(id, status);
        String statusMes = status ? "enabled" : "disabled";
        String message = "The User id: " + id + " has been " + statusMes;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
}
