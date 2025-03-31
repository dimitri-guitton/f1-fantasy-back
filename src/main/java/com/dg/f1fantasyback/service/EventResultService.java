package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.model.entity.racing.EventResult;
import com.dg.f1fantasyback.repository.EventResultRepository;
import org.springframework.stereotype.Service;

@Service
public class EventResultService {
    private final EventResultRepository eventResultRepository;

    public EventResultService(EventResultRepository eventResultRepository) {
        this.eventResultRepository = eventResultRepository;
    }

    public Iterable<EventResult> getResultForRace(Integer raceId) {
        return eventResultRepository.findAllByEvent_Id(raceId);
    }
}
