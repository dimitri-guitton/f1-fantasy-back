package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.model.dto.price.PriceCreateDto;
import com.dg.f1fantasyback.model.dto.price.PriceDto;
import com.dg.f1fantasyback.service.PriceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public Iterable<PriceDto> getPrices() {
        return priceService.getPrices();
    }

    @GetMapping("/{id}")
    public PriceDto getPriceById(@PathVariable UUID id) {
        return priceService.getPriceById(id);
    }

    @GetMapping("/teams/{teamId}")
    public Iterable<PriceDto> getPricesByTeamId(@PathVariable Integer teamId) {
        return priceService.getPricesByTeamId(teamId);
    }

    @GetMapping("/teams/{teamId}/current")
    public PriceDto getCurrentPriceByTeamId(@PathVariable Integer teamId) {
        return priceService.getCurrentPriceByTeamId(teamId);
    }

    @GetMapping("/drivers/{driverId}")
    public Iterable<PriceDto> getPricesByDriverId(@PathVariable Integer driverId) {
        return priceService.getPricesByDriverId(driverId);
    }

    @GetMapping("/drivers/{driverId}/current")
    public PriceDto getCurrentPriceByDriverId(@PathVariable Integer driverId) {
        return priceService.getCurrentPriceByDriverId(driverId);
    }

    @PostMapping
    public PriceDto create(@Valid @RequestBody PriceCreateDto priceCreateDto) {
        return priceService.createPrice(priceCreateDto);
    }
}
