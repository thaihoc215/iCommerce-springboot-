package com.shopme.admin.category.controller;

import com.shopme.admin.category.service.CategoryService;
import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.admin.user.export.FileUploadUtil;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "rest")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("categories")
    public String listAll(Model model) {
        List<Category> categories = categoryService.listAll();
        model.addAttribute("listCategories", categories);
        return "categories/categories";
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
}
