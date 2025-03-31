package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.app_user.UserCreateDto;
import com.dg.f1fantasyback.model.dto.app_user.UserDetailDto;
import com.dg.f1fantasyback.model.dto.app_user.UserUpdateDto;
import com.dg.f1fantasyback.model.entity.AppUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    AppUser toEntity(UserDetailDto userDetailDto);

    AppUser toEntity(UserCreateDto userCreateDto);

    AppUser toEntity(UserUpdateDto userUpdateDto);

    @Mapping(source = "id", target = "id")
    UserDetailDto toDetailDto(AppUser appUser);

    UserCreateDto toCreateDto(AppUser appUser);

    UserUpdateDto toUpdateDto(AppUser appUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppUser partialUpdate(UserCreateDto userCreateDto, @MappingTarget AppUser appUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppUser partialUpdate(UserUpdateDto userUpdateDto, @MappingTarget AppUser appUser);
}