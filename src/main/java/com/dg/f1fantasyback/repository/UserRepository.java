package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByIdAndUsername(UUID id, String username);

    AppUser findFirstByUsername(String username);
}