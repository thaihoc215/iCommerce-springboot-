package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("Select u from User u Where u.email = :email") //we can remove then spring find by auto config
    public User getUserByEmail(@Param("email") String email); // can user 'get'/'find'
}
