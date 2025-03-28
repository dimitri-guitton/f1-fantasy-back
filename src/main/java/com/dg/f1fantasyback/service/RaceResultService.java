package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.repository.RaceResultRepository;
import org.springframework.stereotype.Service;

@Service
public class RaceResultService {
    private final RaceResultRepository raceResultRepository;

    public RaceResultService(RaceResultRepository raceResultRepository) {
        this.raceResultRepository = raceResultRepository;
    }

    public Iterable<RaceResult> getResultForRace(Integer raceId) {
        return raceResultRepository.findAllByRace_Id(raceId);
    }
}
