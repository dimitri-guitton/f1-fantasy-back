package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.user_team.UserTeamCreateDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDetailDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDto;
import com.dg.f1fantasyback.model.entity.UserTeam;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserTeamMapper {
    UserTeam toEntity(UserTeamDto userTeamDto);

    UserTeam toEntity(UserTeamDetailDto userTeamDetailDto);

    UserTeam toEntity(UserTeamCreateDto userTeamCreateDto);

    UserTeamDto toDto(UserTeam userTeam);

    UserTeamDetailDto toDetailDto(UserTeam userTeam);

    UserTeamCreateDto toCreateDto(UserTeam userTeam);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserTeam partialUpdate(UserTeamCreateDto userTeamCreateDto, @MappingTarget UserTeam userTeam);
}