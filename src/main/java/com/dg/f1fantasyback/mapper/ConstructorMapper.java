package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConstructorMapper {
    Constructor toEntity(ConstructorDto constructorDto);

    ConstructorDto toDto(Constructor constructor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Constructor partialUpdate(ConstructorDto constructorDto, @MappingTarget Constructor constructor);
}