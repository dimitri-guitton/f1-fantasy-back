package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {
    Iterable<RaceResult> findAllByRace_Id(Integer raceId);
}