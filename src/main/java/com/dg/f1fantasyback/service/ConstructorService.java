package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.ConstructorMapper;
import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.repository.ConstructorRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ConstructorService {


    private final ConstructorRepository constructorRepository;
    private final ConstructorMapper constructorMapper;

    public ConstructorService(ConstructorRepository constructorRepository, ConstructorMapper constructorMapper) {
        this.constructorRepository = constructorRepository;
        this.constructorMapper = constructorMapper;
    }

    public Iterable<ConstructorDto> getConstructors() {
        return constructorRepository.findAll().stream().map(constructorMapper::toDto).collect(Collectors.toList());
    }

    public ConstructorDto getConstructorById(Integer id) {
        return constructorMapper.toDto(constructorRepository.findById(id).orElseThrow());
    }
}
