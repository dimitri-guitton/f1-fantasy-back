package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.racing.Event;
import com.dg.f1fantasyback.model.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByTypeAndGrandPrix_Id(EventType type, Integer grandPrixId);

    @Transactional
    @Modifying
    @Query("update Event r set r.isCalculated = ?1")
    void updateIsCalculatedBy(@NonNull Boolean isCalculated);

    List<Event> findAllByIsCalculatedOrderByTypeAsc(Boolean isCalculated);

    List<Event> findAllByOrderByTypeAscIdAsc();
}