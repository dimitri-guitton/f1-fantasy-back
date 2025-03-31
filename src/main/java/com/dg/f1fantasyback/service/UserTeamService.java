package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.UserTeamMapper;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamCreateDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDetailDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDto;
import com.dg.f1fantasyback.model.entity.Driver;
import com.dg.f1fantasyback.model.entity.Team;
import com.dg.f1fantasyback.model.entity.User;
import com.dg.f1fantasyback.model.entity.UserTeam;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import com.dg.f1fantasyback.repository.TeamRepository;
import com.dg.f1fantasyback.repository.UserTeamRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;
    private final UserTeamMapper userTeamMapper;
    private final DriverRepository driverRepository;
    private final TeamRepository teamRepository;
    private final FantasyRacePointRepository fantasyRacePointRepository;

    public UserTeamService(UserTeamRepository userTeamRepository,
                           UserTeamMapper userTeamMapper,
                           DriverRepository driverRepository, TeamRepository teamRepository, FantasyRacePointRepository fantasyRacePointRepository) {
        this.userTeamRepository = userTeamRepository;
        this.userTeamMapper = userTeamMapper;
        this.driverRepository = driverRepository;
        this.teamRepository = teamRepository;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
    }

    public Iterable<UserTeamDto> getTeams() {
        return userTeamRepository.findAll().stream().map(userTeamMapper::toDto).collect(Collectors.toList());
    }

    public UserTeamDetailDto getTeamById(Long id) {
        return userTeamMapper.toDetailDto(userTeamRepository.findById(id).orElseThrow());
    }

    public UserTeamDetailDto createTeam(User user, UserTeamCreateDto userTeamCreateDto) {
        UserTeam userTeam = userTeamMapper.toEntity(userTeamCreateDto);
        userTeam.setUser(user);

        return userTeamMapper.toDetailDto(userTeamRepository.save(userTeam));
    }

    public UserTeamDetailDto updateTeam(Long id, UserTeamCreateDto userTeamCreateDto) {
        UserTeam userTeam = userTeamRepository.findById(id).orElseThrow();

        Set<Driver> drivers = new HashSet<>(driverRepository.findAllById(userTeamCreateDto.getDriverIds()));

        if (drivers.size() != userTeamCreateDto.getDriverIds().size()) {
            throw new IllegalArgumentException("Invalid driver ids");
        }

        if (drivers.size() > 5) {
            throw new IllegalArgumentException("Too many drivers");
        }

        Set<Team> teams = new HashSet<>(teamRepository.findAllById(userTeamCreateDto.getTeamIds()));

        if (teams.size() != userTeamCreateDto.getTeamIds().size()) {
            throw new IllegalArgumentException("Invalid team ids");
        }

        if (teams.size() > 2) {
            throw new IllegalArgumentException("Too many teams");
        }

        userTeam.setDrivers(drivers);
        userTeam.setTeams(teams);

        return userTeamMapper.toDetailDto(userTeamRepository.save(userTeamMapper.partialUpdate(userTeamCreateDto, userTeam)));
    }

    public void deleteTeam(Long id) {
        userTeamRepository.deleteById(id);
    }

    public Integer getUserTeamPointsOnRace(Long teamId, Integer raceId) {
        Optional<UserTeam> optUserTeam = userTeamRepository.findById(teamId);
        if (optUserTeam.isEmpty()) {
            throw new IllegalArgumentException("Invalid team id");
        }

        UserTeam userTeam = optUserTeam.get();

        Set<Driver> drivers = userTeam.getDrivers();
        Set<Team> teams = userTeam.getTeams();
        if (drivers.size() > 5) {
            throw new IllegalArgumentException("Too many drivers");
        }

        if (teams.size() > 2) {
            throw new IllegalArgumentException("Too many teams");
        }

        Integer teamId1 = teams.stream().findFirst().orElseThrow().getId();
        Integer teamId2 = teams.stream().skip(1).findFirst().orElseThrow().getId();

        Integer driverId1 = drivers.stream().findFirst().orElseThrow().getId();
        Integer driverId2 = drivers.stream().skip(1).findFirst().orElseThrow().getId();
        Integer driverId3 = drivers.stream().skip(2).findFirst().orElseThrow().getId();
        Integer driverId4 = drivers.stream().skip(3).findFirst().orElseThrow().getId();
        Integer driverId5 = drivers.stream().skip(4).findFirst().orElseThrow().getId();

        return fantasyRacePointRepository.getPointsOnRaceByUserTeam(raceId, teamId1, teamId2, driverId1, driverId2, driverId3, driverId4, driverId5);
    }
}
