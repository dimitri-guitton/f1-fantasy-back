package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.chrono.Chronology;

public interface RaceRepository extends JpaRepository<Race, Integer> {
}