package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.racing.EventResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventResultRepository extends JpaRepository<EventResult, Long> {
    Iterable<EventResult> findAllByEvent_Id(Integer eventId);
}