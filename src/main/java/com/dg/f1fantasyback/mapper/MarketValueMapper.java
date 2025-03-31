package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.market_value.MarketValueCreateDto;
import com.dg.f1fantasyback.model.dto.market_value.MarketValueDto;
import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MarketValueMapper {

    MarketValue toEntity(MarketValueDto priceCreateDto);

    MarketValue toEntity(MarketValueCreateDto marketValueCreateDto);

    MarketValueDto toDto(MarketValue marketValue);

    MarketValueCreateDto toCreateDto(MarketValue marketValue);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MarketValue partialUpdate(MarketValueCreateDto marketValueCreateDto, @MappingTarget MarketValue marketValue);
}