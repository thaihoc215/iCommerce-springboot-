package com.example.restservice.dao;

import com.example.restservice.entity.CustomerActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, Long> {
}
