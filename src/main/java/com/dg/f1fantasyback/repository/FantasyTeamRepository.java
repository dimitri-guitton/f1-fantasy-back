package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FantasyTeamRepository extends JpaRepository<FantasyTeam, Long> {
}