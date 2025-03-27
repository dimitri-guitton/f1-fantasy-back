package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.dto.price.PriceDto;
import com.dg.f1fantasyback.model.entity.Price;
import com.dg.f1fantasyback.model.dto.price.PriceCreateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceMapper {

    Price toEntity(PriceDto priceCreateDto);

    Price toEntity(PriceCreateDto priceCreateDto);

    PriceDto toDto(Price price);

    PriceCreateDto toCreateDto(Price price);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Price partialUpdate(PriceCreateDto priceCreateDto, @MappingTarget Price price);
}