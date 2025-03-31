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
    public Iterable<MarketValueDto> getPrices() {
        return marketValueService.getPrices();
    }

    @GetMapping("/{id}")
    public MarketValueDto getPriceById(@PathVariable UUID id) {
        return marketValueService.getPriceById(id);
    }

    @GetMapping("/constructors/{constructorId}")
    public Iterable<MarketValueDto> getPricesByTeamId(@PathVariable Integer constructorId) {
        return marketValueService.getPricesByTeamId(constructorId);
    }

    @GetMapping("/constructors/{constructorId}/current")
    public MarketValueDto getCurrentPriceByTeamId(@PathVariable Integer constructorId) {
        return marketValueService.getCurrentPriceByTeamId(constructorId);
    }

    @GetMapping("/drivers/{driverId}")
    public Iterable<MarketValueDto> getPricesByDriverId(@PathVariable Integer driverId) {
        return marketValueService.getPricesByDriverId(driverId);
    }

    @GetMapping("/drivers/{driverId}/current")
    public MarketValueDto getCurrentPriceByDriverId(@PathVariable Integer driverId) {
        return marketValueService.getCurrentPriceByDriverId(driverId);
    }

    @PostMapping
    public MarketValueDto create(@Valid @RequestBody MarketValueCreateDto marketValueCreateDto) {
        return marketValueService.createPrice(marketValueCreateDto);
    }
}
