package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.FantasyTeamMapper;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDetailDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDto;
import com.dg.f1fantasyback.model.entity.AppUser;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.repository.ConstructorRepository;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import com.dg.f1fantasyback.repository.FantasyTeamRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FantasyTeamService {

    private final FantasyTeamRepository fantasyTeamRepository;
    private final FantasyTeamMapper fantasyTeamMapper;
    private final DriverRepository driverRepository;
    private final ConstructorRepository constructorRepository;
    private final FantasyRacePointRepository fantasyRacePointRepository;

    public FantasyTeamService(FantasyTeamRepository fantasyTeamRepository, FantasyTeamMapper fantasyTeamMapper, DriverRepository driverRepository, ConstructorRepository constructorRepository, FantasyRacePointRepository fantasyRacePointRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.fantasyTeamMapper = fantasyTeamMapper;
        this.driverRepository = driverRepository;
        this.constructorRepository = constructorRepository;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
    }

    public Iterable<FantasyTeamDto> getTeams() {
        return fantasyTeamRepository.findAll().stream().map(fantasyTeamMapper::toDto).collect(Collectors.toList());
    }

    public FantasyTeamDetailDto getTeamById(Long id) {
        return fantasyTeamMapper.toDetailDto(fantasyTeamRepository.findById(id).orElseThrow());
    }

    public FantasyTeamDetailDto createTeam(AppUser appUser, FantasyTeamCreateDto fantasyTeamCreateDto) {
        FantasyTeam fantasyTeam = fantasyTeamMapper.toEntity(fantasyTeamCreateDto);
        fantasyTeam.setUser(appUser);

        return fantasyTeamMapper.toDetailDto(fantasyTeamRepository.save(fantasyTeam));
    }

    public FantasyTeamDetailDto updateTeam(Long id, FantasyTeamCreateDto fantasyTeamCreateDto) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.findById(id).orElseThrow();

        Set<Driver> drivers = new HashSet<>(driverRepository.findAllById(fantasyTeamCreateDto.getDriverIds()));

        if (drivers.size() != fantasyTeamCreateDto.getDriverIds().size()) {
            throw new IllegalArgumentException("Invalid driver ids");
        }

        if (drivers.size() > 5) {
            throw new IllegalArgumentException("Too many drivers");
        }

        Set<Constructor> constructors = new HashSet<>(constructorRepository.findAllById(fantasyTeamCreateDto.getTeamIds()));

        if (constructors.size() != fantasyTeamCreateDto.getTeamIds().size()) {
            throw new IllegalArgumentException("Invalid team ids");
        }

        if (constructors.size() > 2) {
            throw new IllegalArgumentException("Too many teams");
        }

        fantasyTeam.setDrivers(drivers);
        fantasyTeam.setConstructors(constructors);

        return fantasyTeamMapper.toDetailDto(fantasyTeamRepository.save(fantasyTeamMapper.partialUpdate(fantasyTeamCreateDto, fantasyTeam)));
    }

    public void deleteTeam(Long id) {
        fantasyTeamRepository.deleteById(id);
    }

    public Integer getFantasyTeamPointsOnRace(Long teamId, Integer raceId) {
        Optional<FantasyTeam> optUserTeam = fantasyTeamRepository.findById(teamId);
        if (optUserTeam.isEmpty()) {
            throw new IllegalArgumentException("Invalid team id");
        }

        FantasyTeam fantasyTeam = optUserTeam.get();

        Set<Driver> drivers = fantasyTeam.getDrivers();
        Set<Constructor> constructors = fantasyTeam.getConstructors();
        if (drivers.size() > 5) {
            throw new IllegalArgumentException("Too many drivers");
        }

        if (constructors.size() > 2) {
            throw new IllegalArgumentException("Too many teams");
        }

        Integer teamId1 = constructors.stream().findFirst().orElseThrow().getId();
        Integer teamId2 = constructors.stream().skip(1).findFirst().orElseThrow().getId();

        Integer driverId1 = drivers.stream().findFirst().orElseThrow().getId();
        Integer driverId2 = drivers.stream().skip(1).findFirst().orElseThrow().getId();
        Integer driverId3 = drivers.stream().skip(2).findFirst().orElseThrow().getId();
        Integer driverId4 = drivers.stream().skip(3).findFirst().orElseThrow().getId();
        Integer driverId5 = drivers.stream().skip(4).findFirst().orElseThrow().getId();

        return fantasyRacePointRepository.getPointsOnRaceByUserTeam(raceId, teamId1, teamId2, driverId1, driverId2, driverId3, driverId4, driverId5);
    }
}
