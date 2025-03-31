package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.racing.Constructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstructorRepository extends JpaRepository<Constructor, Integer> {
}