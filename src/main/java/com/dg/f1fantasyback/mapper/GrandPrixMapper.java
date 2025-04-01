package com.dg.f1fantasyback.mapper;

import com.dg.f1fantasyback.model.entity.racing.GrandPrix;
import com.dg.f1fantasyback.model.entity.racing.GrandPrixDto;
import com.dg.f1fantasyback.repository.GrandPrixRepository;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GrandPrixMapper {
    GrandPrix toEntity(GrandPrixDto grandPrixDto);

    GrandPrixDto toDto(GrandPrix grandPrix);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GrandPrix partialUpdate(GrandPrixDto grandPrixDto, @MappingTarget GrandPrix grandPrix);

    @Named("mapGrandPrixId")
    default GrandPrix mapGrandPrixId(Integer id, @Context GrandPrixRepository grandPrixRepository) {
        if (id == null) {
            return null;
        }
        return grandPrixRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("GrandPrix not found with id " + id));
    }
}