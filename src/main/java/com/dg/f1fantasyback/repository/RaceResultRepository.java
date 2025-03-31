package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {
    Iterable<RaceResult> findAllByRace_Id(Integer raceId);
}