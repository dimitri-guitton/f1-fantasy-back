package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.repository.ConstructorRepository;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.FantasyTeamRepository;
import com.dg.f1fantasyback.repository.GrandPrixRepository;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DriverRepository.class, ConstructorRepository.class, GrandPrixMapper.class, FantasyTeamMapper.class, ConstructorMapper.class, DriverMapper.class})
public interface FantasyTeamCompositionMapper {
    @Mapping(source = "grandPrixId", target = "grandPrix", qualifiedByName = "mapGrandPrixId")
    @Mapping(source = "fantasyTeamId", target = "fantasyTeam", qualifiedByName = "mapFantasyTeamId")
    @Mapping(source = "constructorIds", target = "constructors", qualifiedByName = "mapConstructorIdsToConstructors")
    @Mapping(source = "driverIds", target = "drivers", qualifiedByName = "mapDriverIdsToDrivers")
    FantasyTeamComposition toEntity(FantasyTeamCompositionCreateDto dto, @Context ConstructorRepository constructorRepository, @Context DriverRepository driverRepository, @Context GrandPrixRepository grandPrixRepository, @Context FantasyTeamRepository fantasyTeamRepository);

    FantasyTeamComposition toEntity(FantasyTeamCompositionDto fantasyTeamCompositionDto);

    FantasyTeamCompositionCreateDto toCreateDto(FantasyTeamComposition fantasyTeamComposition);

    FantasyTeamCompositionDto toDto(FantasyTeamComposition fantasyTeamComposition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "constructorIds", target = "constructors", qualifiedByName = "mapConstructorIdsToConstructors")
    @Mapping(source = "driverIds", target = "drivers", qualifiedByName = "mapDriverIdsToDrivers")
    FantasyTeamComposition partialUpdate(FantasyTeamCompositionCreateDto fantasyTeamCompositionCreateDto, @MappingTarget FantasyTeamComposition fantasyTeamComposition, @Context ConstructorRepository constructorRepository, @Context DriverRepository driverRepository);


    @Named("mapConstructorIdsToConstructors")
    default Set<Constructor> mapConstructorIdsToConstructors(Set<Integer> ids, @Context ConstructorRepository constructorRepository) {
        if (ids == null) {
            return Collections.emptySet();
        }


        return ids.stream()
                  .map(constructorRepository::findById)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toSet());
    }

    @Named("mapDriverIdsToDrivers")
    default Set<Driver> mapDriverIdsToDrivers(Set<Integer> ids, @Context DriverRepository driverRepository) {
        if (ids == null) {
            return Collections.emptySet();
        }
        return ids.stream()
                  .map(driverRepository::findById)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toSet());
    }
}