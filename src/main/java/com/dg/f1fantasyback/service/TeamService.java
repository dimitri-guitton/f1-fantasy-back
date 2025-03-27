package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.TeamMapper;
import com.dg.f1fantasyback.model.dto.team.TeamDto;
import com.dg.f1fantasyback.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TeamService {


    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Iterable<TeamDto> getTeams() {
        return teamRepository.findAll().stream().map(teamMapper::toDto).collect(Collectors.toList());
    }

    public TeamDto getTeamById(Integer id) {
        return teamMapper.toDto(teamRepository.findById(id).orElseThrow());
    }
}
