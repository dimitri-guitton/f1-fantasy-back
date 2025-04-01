package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.FantasyTeamCompositionMapper;
import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import com.dg.f1fantasyback.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FantasyTeamCompositionService {

    private final FantasyTeamCompositionRepository repository;
    private final FantasyTeamCompositionMapper mapper;
    private final GrandPrixRepository grandPrixRepository;
    private final ConstructorRepository constructorRepository;
    private final DriverRepository driverRepository;
    private final FantasyTeamRepository fantasyTeamRepository;

    public FantasyTeamCompositionService(FantasyTeamCompositionRepository repository, @Qualifier("fantasyTeamCompositionMapperImpl") FantasyTeamCompositionMapper mapper, GrandPrixRepository grandPrixRepository, ConstructorRepository constructorRepository, DriverRepository driverRepository, FantasyTeamRepository fantasyTeamRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.grandPrixRepository = grandPrixRepository;
        this.constructorRepository = constructorRepository;
        this.driverRepository = driverRepository;
        this.fantasyTeamRepository = fantasyTeamRepository;
    }

    public FantasyTeamCompositionDto updateComposition(FantasyTeamCompositionCreateDto dto) {
        Optional<FantasyTeamComposition> optionalEntity = repository.findFirstByGrandPrix_IdAndFantasyTeam_Id(dto.getGrandPrixId(), dto.getFantasyTeamId());

        if (optionalEntity.isPresent()) {
            FantasyTeamComposition entity = optionalEntity.get();
            mapper.partialUpdate(dto, entity, constructorRepository, driverRepository);

            if (entity.getDrivers().size() < 5 || entity.getConstructors().size() < 2) {
                throw new IllegalArgumentException("Il faut 5 pilotes différents et 2 écuries différentes");
            }

            repository.save(entity);
            return mapper.toDto(entity);

        } else {
            FantasyTeamComposition newEntity = mapper.toEntity(dto, constructorRepository, driverRepository, grandPrixRepository, fantasyTeamRepository);
            repository.save(newEntity);
            return mapper.toDto(newEntity);
        }

    }
}
