package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.MarketValueMapper;
import com.dg.f1fantasyback.model.dto.market_value.MarketValueCreateDto;
import com.dg.f1fantasyback.model.dto.market_value.MarketValueDto;
import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.repository.ConstructorRepository;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.MarketValueRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarketValueService {


    private final MarketValueRepository marketValueRepository;
    private final MarketValueMapper marketValueMapper;
    private final DriverRepository driverRepository;
    private final ConstructorRepository constructorRepository;

    public MarketValueService(MarketValueRepository marketValueRepository,
                              MarketValueMapper marketValueMapper, DriverRepository driverRepository, ConstructorRepository constructorRepository) {
        this.marketValueRepository = marketValueRepository;
        this.marketValueMapper = marketValueMapper;
        this.driverRepository = driverRepository;
        this.constructorRepository = constructorRepository;
    }

    public Iterable<MarketValueDto> getMarketValues() {
        return marketValueRepository.findAll().stream().map(marketValueMapper::toDto).collect(Collectors.toList());
    }

    public MarketValueDto getMarketValueById(UUID id) {
        return marketValueMapper.toDto(marketValueRepository.findById(id).orElse(null));
    }

    public Iterable<MarketValueDto> getMarketValuesForConstructor(Integer teamId) {
        return marketValueRepository.findAllByConstructor_Id(teamId).stream().map(marketValueMapper::toDto).collect(Collectors.toList());
    }

    public MarketValueDto getCurrentValueForContructor(Integer teamId) {
        return marketValueRepository.findFirstByConstructor_IdOrderByCreatedAtDesc(teamId).map(marketValueMapper::toDto).orElse(null);
    }

    public Iterable<MarketValueDto> getMarketValueForDriver(Integer userId) {
        return marketValueRepository.findAllByDriver_Id((userId)).stream().map(marketValueMapper::toDto).collect(Collectors.toList());
    }

    public MarketValueDto getCurrentValueForDriver(Integer driverId) {
        return marketValueRepository.findFirstByDriver_IdOrderByCreatedAtDesc(driverId).map(marketValueMapper::toDto).orElse(null);
    }

    public MarketValueDto createMarketValue(MarketValueCreateDto marketValueCreateDto) {
        Driver driver = null;
        Constructor constructor = null;

        if (marketValueCreateDto.getDriverId() != null) {
            driver = driverRepository.findById(marketValueCreateDto.getDriverId()).orElse(null);
        }
        if (marketValueCreateDto.getTeamId() != null) {
            constructor = constructorRepository.findById(marketValueCreateDto.getTeamId()).orElse(null);
        }

        if (driver == null && constructor == null) {
            throw new IllegalArgumentException("At least Driver or Team must be found");
        }

        MarketValue newMarketValue = marketValueMapper.toEntity(marketValueCreateDto);
        newMarketValue.setDriver(driver);
        newMarketValue.setConstructor(constructor);

        return marketValueMapper.toDto(marketValueRepository.save(newMarketValue));

    }
}
