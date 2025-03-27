package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.entity.Driver;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverMapper {
    Driver toEntity(DriverDto driverDto);

    DriverDto toDto(Driver driver);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Driver partialUpdate(DriverDto driverDto, @MappingTarget Driver driver);
}