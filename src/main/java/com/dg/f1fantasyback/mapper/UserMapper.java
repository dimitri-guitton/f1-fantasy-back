package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.user.UserCreateDto;
import com.dg.f1fantasyback.model.dto.user.UserDetailDto;
import com.dg.f1fantasyback.model.dto.user.UserUpdateDto;
import com.dg.f1fantasyback.model.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDetailDto userDetailDto);
    User toEntity(UserCreateDto userCreateDto);
    User toEntity(UserUpdateDto userUpdateDto);

    @Mapping(source = "id", target = "id")
    UserDetailDto toDetailDto(User user);
    UserCreateDto toCreateDto(User user);
    UserUpdateDto toUpdateDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserCreateDto userCreateDto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserUpdateDto userUpdateDto, @MappingTarget User user);
}