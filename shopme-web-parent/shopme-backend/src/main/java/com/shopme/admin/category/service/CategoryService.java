package com.shopme.admin.category.service;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public List<Category> listCategoriesUsedInform() {
        List<Category> categoriesInform = new ArrayList<>();
        Iterable<Category> categoriesInDb = categoryRepository.findAll();
        for (Category category : categoriesInDb) {
            if (category.getParent() == null) {
                categoriesInform.add(new Category(category.getId(), category.getName()));
                //System.out.println(category.getName());
                Set<Category> children = category.getChildren();
                for (Category sub : children) {
                    categoriesInform.add(new Category(sub.getId(), "--" + sub.getName()));
                    //System.out.println("--" + sub.getName());
                    listChildren(categoriesInform, sub, 1);
                }
            }
        }
        return categoriesInform;
    }

    private void listChildren(List<Category> categoriesInform, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();
        for (Category sub : children) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            categoriesInform.add(new Category(sub.getId(), name.append(sub.getName()).toString()));
//            System.out.println(sub.getName());
            listChildren(categoriesInform, sub, newSubLevel);
        }
    }

    public Category save(Category user) {
        return categoryRepository.save(user);
    }
}
