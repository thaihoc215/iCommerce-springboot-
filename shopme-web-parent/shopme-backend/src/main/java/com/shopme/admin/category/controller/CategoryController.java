package com.shopme.admin.category.controller;

import com.shopme.admin.category.CategoryPageInfo;
import com.shopme.admin.category.service.CategoryService;
import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.admin.user.export.FileUploadUtil;
import com.shopme.admin.user.service.UserService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("categories")
    public String listAll(@Param("sortDir") String sortDir, Model model) {
        return listByPage(1, sortDir, null, model);
    }

    @GetMapping("categories/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") Integer pageNum,
                             @Param("sortDir") String sortDir, @Param("keyword") String keyword,
                             Model model) {
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";
        }

        if(pageNum == null) {
            pageNum = 1;
        }

        CategoryPageInfo categoryPageInfo = new CategoryPageInfo();
        List<Category> categories = categoryService.listByPage(categoryPageInfo, pageNum, sortDir, keyword);
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("listCategories", categories);
        model.addAttribute("reverseSortDir", reverseSortDir);

        long startCount = (pageNum - 1) * CategoryService.ROOT_CAT_PER_PAGE + 1;
        long endCount = startCount + CategoryService.ROOT_CAT_PER_PAGE - 1;
        if (endCount > categoryPageInfo.getTotalElements()) {
            endCount = categoryPageInfo.getTotalElements();
        }
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", categoryPageInfo.getTotalPages());
        model.addAttribute("totalItems", categoryPageInfo.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("sortField", "name");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);

        return "categories/categories";

        /*List<User> listUsers = page.getContent();
        long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }*/
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInform();
        Category category = new Category();
        category.setEnabled(true);

        model.addAttribute("category", category);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Create New Category");

        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category, RedirectAttributes redirectAttributes,
                               @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileNameSelected = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileNameSelected);
            Integer catId = categoryService.save(category).getId();

            String uploadDir = "../category-images/" + catId; //to go to shopme parent
            FileUploadUtil.cleanDirectory(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileNameSelected, multipartFile);
        } else {
            categoryService.save(category);
        }

        redirectAttributes.addFlashAttribute("message", "The category has been saved " +
                "successfully");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            Category catById = categoryService.getCategoryById(id);
            model.addAttribute("category", catById);
            model.addAttribute("pageTitle", "Edit Category Id: " + id);

            List<Category> listCategories = categoryService.listCategoriesUsedInform();
            model.addAttribute("listCategories", listCategories);

            return "categories/category_form";
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/categories";
        }
    }


    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateCatStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean status,
                                   RedirectAttributes redirectAttributes) {
        categoryService.updateCategoryEnabledStatus(id, status);
        String statusMes = status ? "enabled" : "disabled";
        String message = "Category id: " + id + " has been " + statusMes;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            String uploadDir = "../category-images/" + id; //to go to shopme parent
            FileUploadUtil.removeDir(uploadDir);

            redirectAttributes.addFlashAttribute("message", "The user ID: " + id +
                    " has been deleted successfully");
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/categories";
    }
}
