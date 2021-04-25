package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("select c from Category  c where c.parent.id is NULL ")
    List<Category> findRootCategories(Sort sort);

    Category findByName(String name);

    Category findByAlias(String alias);

    //@Query("Update Category c set c.enabled = ?2 where c.id = ?1")
    @Query("Update Category c set c.enabled = :enabled where c.id = :id")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Long countById(Integer id);
}
