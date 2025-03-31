package com.dg.f1fantasyback.controller.fantasy;

import com.dg.f1fantasyback.exception.PointCalculatorException;
import com.dg.f1fantasyback.model.entity.racing.Event;
import com.dg.f1fantasyback.repository.EventRepository;
import com.dg.f1fantasyback.service.scoring.ScoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fantasy")
public class CalculatorController {
    private final ScoringService scoringService;
    private final EventRepository eventRepository;

    public CalculatorController(ScoringService scoringService, EventRepository eventRepository) {
        this.scoringService = scoringService;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/compute-all")
    public void calculateRacePoints() {
        scoringService.computeUncalculedRaces();
    }

    @GetMapping("/compute/events/{id}")
    public void calculateRacePoints(@PathVariable Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new PointCalculatorException("Invalid Race ID"));

        scoringService.computeRacePoints(event);
    }
}
