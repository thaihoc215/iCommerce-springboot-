package com.shopme.admin.brand.controller;

import com.shopme.admin.brand.service.BrandService;
import com.shopme.admin.category.service.CategoryService;
import com.shopme.admin.exception.BrandNotFoundException;
import com.shopme.admin.user.export.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String newBrand(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInform();
        model.addAttribute("listCategories", listCategories);

        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        model.addAttribute("pageTitle", "Create New Brands");

        return "brands/brand_form";
    }


    @PostMapping("/brands/save")
    public String saveBrand(Brand brand, RedirectAttributes redirectAttributes,
                               @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileNameSelected = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileNameSelected);
            Integer brandId = brandService.save(brand).getId();

            String uploadDir = "../brand-logos/" + brandId; //to go to shopme parent
            FileUploadUtil.cleanDirectory(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileNameSelected, multipartFile);
        } else {
            brandService.save(brand);
        }

        redirectAttributes.addFlashAttribute("message", "The brand has been saved " +
                "successfully");
        return "redirect:/brands";
    }

    @GetMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable("id") Integer id, Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            Brand brandById = brandService.getBrandById(id);
            model.addAttribute("brand", brandById);
            model.addAttribute("pageTitle", "Edit Brand Id: " + id);

            List<Category> listCategories = categoryService.listCategoriesUsedInform();
            model.addAttribute("listCategories", listCategories);

            return "brands/brand_form";
        } catch (BrandNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/brands";
        }
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteBrand(id);
            String uploadDir = "../brand-logos/" + id; //to go to shopme parent
            FileUploadUtil.removeDir(uploadDir);

            redirectAttributes.addFlashAttribute("message", "The user ID: " + id +
                    " has been deleted successfully");
        } catch (BrandNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/brands";
    }
}
