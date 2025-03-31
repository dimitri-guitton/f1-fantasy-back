package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.exception.PointCalculatorException;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Event;
import com.dg.f1fantasyback.model.entity.racing.EventResult;
import com.dg.f1fantasyback.model.enums.EventType;
import com.dg.f1fantasyback.repository.EventRepository;
import com.dg.f1fantasyback.repository.EventResultRepository;
import com.dg.f1fantasyback.service.FantasyScoreService;
import com.dg.f1fantasyback.service.EventResultService;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScoringService {

    private final EventResultService eventResultService;
    private final FantasyScoreService fantasyScoreService;
    private final EventResultRepository eventResultRepository;
    private final EventRepository eventRepository;

    public ScoringService(EventResultService eventResultService, FantasyScoreService fantasyScoreService, EventResultRepository eventResultRepository, EventRepository eventRepository) {
        this.eventResultService = eventResultService;
        this.fantasyScoreService = fantasyScoreService;
        this.eventResultRepository = eventResultRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Retourne le nombre de point "Fantasy" d'un pilote
     */
    private int computeDriverPoints(EventResult result) {
        EventType type = result.getEvent().getType();
        ScoringStrategy strategy = ScoringStrategyFactory.getStrategy(type);
        return strategy.calculateDriverPoints(result);
    }

    /**
     * Retourne le nombre de point "Fantasy" d'une écurie
     */
    private int computeTeamPoints(EventResult resultDriver1, EventResult resultDriver2) {
        EventType type = resultDriver1.getEvent().getType();
        ScoringStrategy strategy = ScoringStrategyFactory.getStrategy(type);
        return strategy.calculateTeamPoints(resultDriver1, resultDriver2);
    }

    public void computeEventPoints(Event event) {
        if (event.getIsCalculated()) {
            throw new PointCalculatorException("This race has already been calculated");
        }

        if (!EnumSet.of(EventType.GP, EventType.QUALIFYING, EventType.SPRINT).contains(event.getType())) {
            throw new PointCalculatorException("This race type is not supported");
        }

        Iterable<EventResult> results = eventResultService.getResultForRace(event.getId());
        results.forEach(result -> {
            int points = computeDriverPoints(result);
            fantasyScoreService.createDriverPoint(event, result.getDriver(), points);
        });

        Map<Constructor, List<EventResult>> resultsGroupedByTeam = StreamSupport.stream(results.spliterator(), false)
                                                                                .collect(Collectors.groupingBy(rr -> rr.getDriver().getConstructor()));

        for (var entry : resultsGroupedByTeam.entrySet()) {
            int points = computeTeamPoints(entry.getValue().get(0), entry.getValue().get(1));
            fantasyScoreService.createConstructorPoint(event, entry.getKey(), points);
        }

        event.setIsCalculated(true);
        eventRepository.saveAndFlush(event);
    }

    public void computeUncalculedRaces() {
        List<Event> events = eventRepository.findAllByIsCalculatedOrderByTypeAsc(false);

        events.forEach(this::computeEventPoints);
    }
}
