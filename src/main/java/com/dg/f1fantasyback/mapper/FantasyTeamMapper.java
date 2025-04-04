package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDetailDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDto;
import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import com.dg.f1fantasyback.repository.FantasyTeamRepository;
import org.mapstruct.*;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {FantasyTeamCompositionMapper.class})
public interface FantasyTeamMapper {
    FantasyTeam toEntity(FantasyTeamDto fantasyTeamDto);

    FantasyTeam toEntity(FantasyTeamDetailDto fantasyTeamDetailDto);

    FantasyTeam toEntity(FantasyTeamCreateDto fantasyTeamCreateDto);

    FantasyTeamDto toDto(FantasyTeam fantasyTeam);

    @Mapping(source = "fantasyTeamCompositions", target = "composition", qualifiedByName = "mapLastComposition")
    FantasyTeamDetailDto toDetailDto(FantasyTeam fantasyTeam, @Context FantasyTeamCompositionMapper compositionMapper);

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


    @Named("mapLastComposition")
    default FantasyTeamCompositionDto mapLastComposition(Set<FantasyTeamComposition> compositions, @Context FantasyTeamCompositionMapper compositionMapper) {
        if (compositions == null || compositions.isEmpty()) {
            return null;
        }

        FantasyTeamComposition last = compositions.stream()
                                                  .reduce((first, second) -> second)
                                                  .orElse(null)
                ;

        return compositionMapper.toDto(last);
    }
}