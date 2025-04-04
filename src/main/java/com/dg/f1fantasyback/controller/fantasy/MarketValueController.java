package com.dg.f1fantasyback.controller.fantasy;

import com.dg.f1fantasyback.model.dto.market_value.MarketValueCreateDto;
import com.dg.f1fantasyback.model.dto.market_value.MarketValueDto;
import com.dg.f1fantasyback.service.MarketValueService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/market-values")
public class MarketValueController {
    private final MarketValueService marketValueService;

    public MarketValueController(MarketValueService marketValueService) {
        this.marketValueService = marketValueService;
    }

    @GetMapping
    public Iterable<MarketValueDto> getMarketValues() {
        return marketValueService.getMarketValues();
    }

    @GetMapping("/{id}")
    public MarketValueDto getMarketValuById(@PathVariable UUID id) {
        return marketValueService.getMarketValueById(id);
    }

    @GetMapping("/constructors/{constructorId}")
    public Iterable<MarketValueDto> getMarketValuesForConstructor(@PathVariable Integer constructorId) {
        return marketValueService.getMarketValuesForConstructor(constructorId);
    }

    @GetMapping("/constructors/current")
    public Iterable<MarketValueDto> getMarketValuesForConstructor() {
        return marketValueService.getMarketValuesForConstructors();
    }

    @GetMapping("/constructors/{constructorId}/current")
    public MarketValueDto getCurrentValueForContructor(@PathVariable Integer constructorId) {
        return marketValueService.getCurrentValueForContructor(constructorId);
    }

    @GetMapping("/drivers/{driverId}")
    public Iterable<MarketValueDto> getMarketValueForDriver(@PathVariable Integer driverId) {
        return marketValueService.getMarketValueForDriver(driverId);
    }

    @GetMapping("/drivers/current")
    public Iterable<MarketValueDto> getMarketValuesForDrivers() {
        return marketValueService.getMarketValuesForDrivers();
    }

    @GetMapping("/drivers/{driverId}/current")
    public MarketValueDto getCurrentValueForDriver(@PathVariable Integer driverId) {
        return marketValueService.getCurrentValueForDriver(driverId);
    }

    @PostMapping
    public MarketValueDto create(@Valid @RequestBody MarketValueCreateDto marketValueCreateDto) {
        return marketValueService.createMarketValue(marketValueCreateDto);
    }
}
