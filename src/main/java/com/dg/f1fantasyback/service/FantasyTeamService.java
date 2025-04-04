package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.FantasyTeamCompositionMapper;
import com.dg.f1fantasyback.mapper.FantasyTeamMapper;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDetailDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDto;
import com.dg.f1fantasyback.model.entity.AppUser;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import com.dg.f1fantasyback.repository.FantasyTeamRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FantasyTeamService {

    private final FantasyTeamRepository fantasyTeamRepository;
    private final FantasyTeamMapper fantasyTeamMapper;
    private final FantasyTeamCompositionMapper compositionMapper;

    public FantasyTeamService(FantasyTeamRepository fantasyTeamRepository, FantasyTeamMapper fantasyTeamMapper, @Qualifier("fantasyTeamCompositionMapperImpl") FantasyTeamCompositionMapper compositionMapper) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.fantasyTeamMapper = fantasyTeamMapper;
        this.compositionMapper = compositionMapper;
    }

    public Iterable<FantasyTeamDto> getTeams() {
        return fantasyTeamRepository.findAll().stream().map(fantasyTeamMapper::toDto).collect(Collectors.toList());
    }

    public FantasyTeamDetailDto getTeamById(Long id) {
        return fantasyTeamMapper.toDetailDto(fantasyTeamRepository.findById(id).orElseThrow(), compositionMapper);
    }

    public FantasyTeamDetailDto createTeam(AppUser appUser, FantasyTeamCreateDto fantasyTeamCreateDto) {
        FantasyTeam fantasyTeam = fantasyTeamMapper.toEntity(fantasyTeamCreateDto);
        fantasyTeam.setUser(appUser);

        return fantasyTeamMapper.toDetailDto(fantasyTeamRepository.save(fantasyTeam), compositionMapper);
    }

    public FantasyTeamDetailDto updateTeam(Long id, FantasyTeamCreateDto fantasyTeamCreateDto) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.findById(id).orElseThrow();

        return fantasyTeamMapper.toDetailDto(fantasyTeamRepository.save(fantasyTeamMapper.partialUpdate(fantasyTeamCreateDto, fantasyTeam)), compositionMapper);
    }

    public void deleteTeam(Long id) {
        fantasyTeamRepository.deleteById(id);
    }

    public Integer getFantasyTeamPointsOnRace(Long teamId, Integer raceId) {
        return null;
    }

    public List<FantasyTeamDetailDto> getUserTeams(AppUser currentUser) {
        return fantasyTeamRepository.findByUser_IdOrderByLabelAsc(currentUser.getId())
                                    .stream()
                                    .map(team -> fantasyTeamMapper.toDetailDto(team, compositionMapper))
                                    .collect(Collectors.toList());
    }
}
