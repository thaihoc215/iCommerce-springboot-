package com.example.restservice.dao;

import com.example.restservice.entity.PriceTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTrackingRepository extends JpaRepository<PriceTracking, Long> {
}
