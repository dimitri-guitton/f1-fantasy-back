package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.user_team.UserTeamCreateDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDetailDto;
import com.dg.f1fantasyback.model.entity.UserTeam;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserTeamMapper {
    UserTeam toEntity(UserTeamDetailDto userTeamCreateDto);

    UserTeam toEntity(UserTeamCreateDto userTeamCreateDto);

    UserTeamDetailDto toDetailDto(UserTeam userTeam);

    UserTeamCreateDto toCreateDto(UserTeam userTeam);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserTeam partialUpdate(UserTeamCreateDto userTeamCreateDto, @MappingTarget UserTeam userTeam);
}