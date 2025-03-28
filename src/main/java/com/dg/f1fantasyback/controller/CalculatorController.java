package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.repository.RaceRepository;
import com.dg.f1fantasyback.service.race_point_calculator.RaceCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private final RaceCalculator raceCalculator;
    private final RaceRepository raceRepository;

    public CalculatorController(RaceCalculator raceCalculator, RaceRepository raceRepository) {
        this.raceCalculator = raceCalculator;
        this.raceRepository = raceRepository;
    }

    @GetMapping("/{id}/race/calculate")
    public void calculateRacePoints(@PathVariable Integer id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid Race ID"));

        raceCalculator.calculateRacePoints(race);
    }
}
