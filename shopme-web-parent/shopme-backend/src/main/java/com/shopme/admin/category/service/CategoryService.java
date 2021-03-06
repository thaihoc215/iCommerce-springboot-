package com.shopme.admin.category.service;

import com.shopme.admin.category.CategoryPageInfo;
import com.shopme.admin.category.CategoryRepository;
import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
@Transactional
public class CategoryService {

    public static final int ROOT_CAT_PER_PAGE = 4;

    @Autowired
    private CategoryRepository catRepo;

    public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNum, String sortDir, String keyword) {
        Sort sort = Sort.by("name");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CAT_PER_PAGE, sort);

        Page<Category> pageCategories;
        if (keyword != null && !keyword.isEmpty()) {
            pageCategories = catRepo.searchCategories(keyword, pageable);
        } else {
            pageCategories = catRepo.findRootCategories(pageable);
        }

        pageInfo.setTotalElements(pageCategories.getTotalElements());
        pageInfo.setTotalPages(pageCategories.getTotalPages());

        List<Category> rootCategories = pageCategories.getContent();
        if (keyword != null && !keyword.isEmpty()) {
            //no need to show hierarchical in case use filter function
            for (Category category : rootCategories) {
                // to avoid can delete cat have children
                category.setHasChildren(category.getChildren().size() > 0);
            }
            return rootCategories;
        } else {
            return listHierarchicalCategories(rootCategories, sortDir);
        }
    }

    /**
     * List all categories and sub for showing in cat page
     *
     * @param rootCategories
     * @return
     */
    public List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCats = new ArrayList<>();
        for (Category rootCat : rootCategories) {
            hierarchicalCats.add(new Category(rootCat));
            Set<Category> children = sortSubCategory(rootCat.getChildren(), sortDir);
            for (Category subCat : children) {
                hierarchicalCats.add(new Category(subCat, "--" + subCat.getName()));
                //System.out.println("--" + sub.getName());
                listSubHierarchicalCategories(hierarchicalCats, subCat, 1, sortDir);
            }
        }
        return hierarchicalCats;
    }

    private void listSubHierarchicalCategories(List<Category> categories, Category parent, int subLevel, String sortDir) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategory(parent.getChildren(), sortDir);
        for (Category sub : children) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            categories.add(new Category(sub, name.append(sub.getName()).toString()));
            listSubHierarchicalCategories(categories, sub, newSubLevel, sortDir);
        }
    }

    /**
     * For temporary showing in form
     *
     * @return
     */
    public List<Category> listCategoriesUsedInform() {
        List<Category> categoriesInform = new ArrayList<>();
        Iterable<Category> categoriesInDb = catRepo.findRootCategories(Sort.by("name").ascending());
        for (Category category : categoriesInDb) {
            if (category.getParent() == null) {
                categoriesInform.add(new Category(category.getId(), category.getName()));
                //System.out.println(category.getName());
                Set<Category> children = sortSubCategory(category.getChildren());
                for (Category sub : children) {
                    categoriesInform.add(new Category(sub.getId(), "--" + sub.getName()));
                    //System.out.println("--" + sub.getName());
                    listSubCategoriesUseInForm(categoriesInform, sub, 1);
                }
            }
        }
        return categoriesInform;
    }

    private void listSubCategoriesUseInForm(List<Category> categoriesInform, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategory(parent.getChildren());
        for (Category sub : children) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            categoriesInform.add(new Category(sub.getId(), name.append(sub.getName()).toString()));
            listSubCategoriesUseInForm(categoriesInform, sub, newSubLevel);
        }
    }

    /**
     * Add or edit a category
     *
     * @param category cat need to save
     * @return cat after saved
     */
    public Category save(Category category) {
        return catRepo.save(category);
    }

    /**
     * Find a category by Id (to edit...)
     *
     * @param id catId
     * @return a category
     * @throws CategoryNotFoundException
     */
    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        try {
            return catRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CategoryNotFoundException("Could not find any user with id: " + id);
        }
    }

    public String checkUnique(Integer id, String name, String alias) {
        boolean isCreatingNew = (id == null || id == 0);

        Category category = catRepo.findByName(name);

        if (isCreatingNew) {
            if (category != null) {
                return "DuplicateName";
            } else {
                if (catRepo.findByAlias(alias) != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (category != null && !category.getId().equals(id)) {
                return "DuplicateName";
            }

            Category categoryByAlias = catRepo.findByAlias(alias);
            if (categoryByAlias != null && !categoryByAlias.getId().equals(id)) {
                return "DuplicateAlias";
            }
        }
        return "OK";
    }

    private SortedSet<Category> sortSubCategory(Set<Category> children) {
        return sortSubCategory(children, "asc");
    }

    private SortedSet<Category> sortSubCategory(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                if (sortDir.equals("asc")) {
                    return cat1.getName().compareTo(cat2.getName());
                } else {
                    return cat2.getName().compareTo(cat1.getName());
                }

            }
        });

        sortedChildren.addAll(children);
        return sortedChildren;
    }

    /**
     * Update category status enable/disable
     * @param id cat id
     * @param enable value of status
     */
    public void updateCategoryEnabledStatus(Integer id, boolean enable) {
        catRepo.updateEnabledStatus(id, enable);
    }

    /**
     * Find and delete category by Id
     * @param id
     */
    public void deleteCategory(Integer id) throws CategoryNotFoundException {
        Long countById = catRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new CategoryNotFoundException("Could not find any category with id: " + id);
        }
        catRepo.deleteById(id);
    }
}
