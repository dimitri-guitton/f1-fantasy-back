package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.DriverMapper;
import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverService(DriverRepository driverRepository,
                         DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    public Iterable<DriverDto> getDrivers() {
        return driverRepository.findAll().stream().map(driverMapper::toDto).collect(Collectors.toList());
    }

    public DriverDto getDriverById(Integer id) {
        return driverMapper.toDto(driverRepository.findById(id).orElseThrow());
    }
}
