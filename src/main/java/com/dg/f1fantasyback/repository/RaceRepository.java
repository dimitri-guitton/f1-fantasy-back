package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Integer> {
    Optional<Race> findByTypeAndGrandPrix_Id(RaceTypeEnum type, Integer grandPrixId);

    @Transactional
    @Modifying
    @Query("update Race r set r.isCalculated = ?1")
    void updateIsCalculatedBy(@NonNull Boolean isCalculated);

    List<Race> findAllByIsCalculatedOrderByTypeAsc(Boolean isCalculated);
}