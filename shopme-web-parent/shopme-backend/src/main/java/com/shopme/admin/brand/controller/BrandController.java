package com.shopme.admin.brand.controller;

import com.shopme.admin.brand.service.BrandService;
import com.shopme.admin.category.service.CategoryService;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/brands")
    public String listAll(Model model) {
        return listByPage(1, model, "name", "asc", null);
    }

    @GetMapping("/brands/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") Integer pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {

        model.addAttribute("listBrands", brandService.listAllBrands());

        /*Page<Brand> brandPage = brandService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Brand> brands = brandPage.getContent();

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("listBrands", brands);
        model.addAttribute("reverseSortDir", reverseSortDir);*/



        /*long startCount = (pageNum - 1) * BrandService.BRAND_PER_PAGE + 1;
        long endCount = startCount + CategoryService.BRAND_PER_PAGE - 1;
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
        model.addAttribute("keyword", keyword);*/



        return "brands/brands";

        /*List<User> listUsers = page.getContent();
        long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }*/
    }

    @GetMapping("/brands/new")
    public String newCategory(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInform();
        model.addAttribute("listCategories", listCategories);

        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        model.addAttribute("pageTitle", "Create New Brands");

        return "brands/brand_form";
    }

    /*
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
    }*/
}
