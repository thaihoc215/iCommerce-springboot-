package com.shopme.admin.category.controller;

import com.shopme.admin.category.service.CategoryService;
import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.admin.user.export.FileUploadUtil;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @PostMapping("/categories/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
        return categoryService.checkUnique(id, name, alias);
    }
}
