package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
}