package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.racing.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
}