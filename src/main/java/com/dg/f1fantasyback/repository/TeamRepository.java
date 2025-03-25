package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}