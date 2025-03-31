package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketValueRepository extends JpaRepository<MarketValue, UUID> {
    List<MarketValue> findAllByConstructor_Id(Integer constructorId);

    List<MarketValue> findAllByDriver_Id(Integer driverId);

    Optional<MarketValue> findFirstByDriver_IdOrderByCreatedAtDesc(Integer driverId);

    Optional<MarketValue> findFirstByConstructor_IdOrderByCreatedAtDesc(Integer constructorId);

}