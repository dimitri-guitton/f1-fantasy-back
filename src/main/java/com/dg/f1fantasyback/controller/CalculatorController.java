package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.exception.PointCalculatorException;
import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.repository.RaceRepository;
import com.dg.f1fantasyback.service.scoring.ScoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private final ScoringService scoringService;
    private final RaceRepository raceRepository;

    public CalculatorController(ScoringService scoringService, RaceRepository raceRepository) {
        this.scoringService = scoringService;
        this.raceRepository = raceRepository;
    }

    @GetMapping("/calculate-all")
    public void calculateRacePoints() {
        scoringService.computeUncalculedRaces();
    }

    @GetMapping("/{id}/race/calculate")
    public void calculateRacePoints(@PathVariable Integer id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new PointCalculatorException("Invalid Race ID"));

        scoringService.computeRacePoints(race);
    }
}
