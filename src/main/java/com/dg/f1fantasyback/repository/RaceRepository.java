package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Integer> {
}