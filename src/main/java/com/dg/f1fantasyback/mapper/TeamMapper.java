package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.team.TeamDto;
import com.dg.f1fantasyback.model.entity.Team;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper {
    Team toEntity(TeamDto teamDto);

    TeamDto toDto(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDto teamDto, @MappingTarget Team team);
}