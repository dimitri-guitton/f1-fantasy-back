package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FantasyTeamCompositionRepository extends JpaRepository<FantasyTeamComposition, Long> {
    Optional<FantasyTeamComposition> findFirstByGrandPrix_IdAndFantasyTeam_Id(Integer grandPrixId, Long fantasyTeamId);
}