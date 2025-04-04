package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.dto.market_value.MarketValueDto;
import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketValueRepository extends JpaRepository<MarketValue, UUID> {
    List<MarketValue> findAllByConstructor_Id(Integer constructorId);

    List<MarketValue> findAllByDriver_Id(Integer driverId);

    Optional<MarketValue> findFirstByDriver_IdOrderByCreatedAtDesc(Integer driverId);

    Optional<MarketValue> findFirstByConstructor_IdOrderByCreatedAtDesc(Integer constructorId);

    List<MarketValue> findByConstructorNotNull();

    @Query("SELECT mv FROM MarketValue mv WHERE mv.createdAt IN (SELECT MAX(mv2.createdAt) FROM MarketValue mv2 WHERE mv2.constructor IS NOT NULL GROUP BY mv2.constructor.id)")
    List<MarketValue> findLatestMarketValuesForConstructors();

    @Query("SELECT mv FROM MarketValue mv WHERE mv.createdAt IN (SELECT MAX(mv2.createdAt) FROM MarketValue mv2 WHERE mv2.driver IS NOT NULL GROUP BY mv2.driver.id)")
    List<MarketValue> findLatestMarketValuesForDrivers();
}