package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.PriceMapper;
import com.dg.f1fantasyback.model.dto.price.PriceCreateDto;
import com.dg.f1fantasyback.model.dto.price.PriceDto;
import com.dg.f1fantasyback.model.entity.Driver;
import com.dg.f1fantasyback.model.entity.Price;
import com.dg.f1fantasyback.model.entity.Team;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.PriceRepository;
import com.dg.f1fantasyback.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PriceService {


    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final DriverRepository driverRepository;
    private final TeamRepository teamRepository;

    public PriceService(PriceRepository priceRepository,
                        PriceMapper priceMapper, DriverRepository driverRepository, TeamRepository teamRepository) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
        this.driverRepository = driverRepository;
        this.teamRepository = teamRepository;
    }

    public Iterable<PriceDto> getPrices() {
        return priceRepository.findAll().stream().map(priceMapper::toDto).collect(Collectors.toList());
    }

    public PriceDto getPriceById(UUID id) {
        return priceMapper.toDto(priceRepository.findById(id).orElse(null));
    }

    public Iterable<PriceDto> getPricesByTeamId(Integer teamId) {
        return priceRepository.findAllByTeam_Id(teamId).stream().map(priceMapper::toDto).collect(Collectors.toList());
    }

    public PriceDto getCurrentPriceByTeamId(Integer teamId) {
        return priceRepository.findFirstByTeam_IdOrderByCreatedAtDesc(teamId).map(priceMapper::toDto).orElse(null);
    }

    public Iterable<PriceDto> getPricesByDriverId(Integer userId) {
        return priceRepository.findAllByDriver_Id((userId)).stream().map(priceMapper::toDto).collect(Collectors.toList());
    }

    public PriceDto getCurrentPriceByDriverId(Integer driverId) {
        return priceRepository.findFirstByDriver_IdOrderByCreatedAtDesc(driverId).map(priceMapper::toDto).orElse(null);
    }

    public PriceDto createPrice(PriceCreateDto priceCreateDto) {
        Driver driver = null;
        Team team = null;

        if (priceCreateDto.getDriverId() != null) {
            driver = driverRepository.findById(priceCreateDto.getDriverId()).orElse(null);
        }
        if (priceCreateDto.getTeamId() != null) {
            team = teamRepository.findById(priceCreateDto.getTeamId()).orElse(null);
        }

        if (driver == null && team == null) {
            throw new IllegalArgumentException("At least Driver or Team must be found");
        }

        Price newPrice = priceMapper.toEntity(priceCreateDto);
        newPrice.setDriver(driver);
        newPrice.setTeam(team);

        return priceMapper.toDto(priceRepository.save(newPrice));

    }
}
