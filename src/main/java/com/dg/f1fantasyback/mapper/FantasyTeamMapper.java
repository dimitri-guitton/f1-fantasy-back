package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDetailDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import com.dg.f1fantasyback.repository.FantasyTeamRepository;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FantasyTeamMapper {
    FantasyTeam toEntity(FantasyTeamDto fantasyTeamDto);

    FantasyTeam toEntity(FantasyTeamDetailDto fantasyTeamDetailDto);

    FantasyTeam toEntity(FantasyTeamCreateDto fantasyTeamCreateDto);

    FantasyTeamDto toDto(FantasyTeam fantasyTeam);

    FantasyTeamDetailDto toDetailDto(FantasyTeam fantasyTeam);

    FantasyTeamCreateDto toCreateDto(FantasyTeam fantasyTeam);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FantasyTeam partialUpdate(FantasyTeamCreateDto fantasyTeamCreateDto, @MappingTarget FantasyTeam fantasyTeam);

    @Named("mapFantasyTeamId")
    default FantasyTeam mapFantasyTeamId(Long id, @Context FantasyTeamRepository fantasyTeamRepository) {
        if (id == null) {
            return null;
        }
        return fantasyTeamRepository.findById(id)
                                    .orElseThrow(() -> new IllegalArgumentException("GrandPrix not found with id " + id));
    }

}