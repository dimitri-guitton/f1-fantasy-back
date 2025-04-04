package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.driver.DriverDetailDto;
import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverMapper {
    Driver toEntity(DriverDto driverDto);

    Driver toEntity(DriverDetailDto driverDetailDto);


    DriverDto toDto(Driver driver);

    @Mapping(source = "marketValues", target = "marketValue", qualifiedByName = "getLastMarketValue")
    DriverDetailDto toDetailDto(Driver driver);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Driver partialUpdate(DriverDto driverDto, @MappingTarget Driver driver);

    @Named("getLastMarketValue")
    default Long getLastMarketValue(Set<MarketValue> marketValues) {
        return marketValues.stream()
                           .max(Comparator.comparing(MarketValue::getCreatedAt))
                           .map(MarketValue::getValue)
                           .orElse(0L);
    }
}