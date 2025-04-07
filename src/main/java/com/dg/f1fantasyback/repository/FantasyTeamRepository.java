package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FantasyTeamRepository extends JpaRepository<FantasyTeam, Long> {
    List<FantasyTeam> findByUser_IdOrderByLabelAsc(UUID userId);

    int countFantasyTeamByUser_Id(UUID userId);
}