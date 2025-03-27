package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, UUID> {
    List<Price> findAllByTeam_Id(Integer teamId);

    List<Price> findAllByDriver_Id(Integer driverId);

    Optional<Price> findFirstByDriver_IdOrderByCreatedAtDesc(Integer driverId);

    Optional<Price> findFirstByTeam_IdOrderByCreatedAtDesc(Integer teamId);

}