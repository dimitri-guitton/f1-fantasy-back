package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.constructor.ConstructorDetailDto;
import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConstructorMapper {
    Constructor toEntity(ConstructorDto constructorDto);

    Constructor toEntity(ConstructorDetailDto constructorDetailDto);

    ConstructorDto toDto(Constructor constructor);

    @Mapping(source = "marketValues", target = "marketValue", qualifiedByName = "getLastMarketValue")
    ConstructorDetailDto toDetailDto(Constructor constructor);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Constructor partialUpdate(ConstructorDto constructorDto, @MappingTarget Constructor constructor);

    @Named("getLastMarketValue")
    default Long getLastMarketValue(Set<MarketValue> marketValues) {
        return marketValues.stream()
                           .max(Comparator.comparing(MarketValue::getCreatedAt))
                           .map(MarketValue::getValue)
                           .orElse(0L);
    }
}