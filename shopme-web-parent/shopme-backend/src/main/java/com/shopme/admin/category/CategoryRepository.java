package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    /**
     * find category with sort for use inform
     * @param sort
     * @return
     */
    @Query("select c from Category c where c.parent.id is NULL ")
    List<Category> findRootCategories(Sort sort);

    /**
     * find category with paging
     * @param pageable
     * @return
     */
    @Query("select c from Category c where c.parent.id is NULL ")
    Page<Category> findRootCategories(Pageable pageable);

    @Query("select c from Category c where lower(c.name)" +
            " like lower(concat('%', ?1,'%'))")
    Page<Category> searchCategories(String keyword, Pageable pageable);

    Category findByName(String name);

    Category findByAlias(String alias);

    //@Query("Update Category c set c.enabled = ?2 where c.id = ?1")
    @Query("Update Category c set c.enabled = :enabled where c.id = :id")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Long countById(Integer id);
}
