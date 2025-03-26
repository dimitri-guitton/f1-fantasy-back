package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.mapper.UserTeamMapper;
import com.dg.f1fantasyback.model.dto.user.UserDetailDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamCreateDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDetailDto;
import com.dg.f1fantasyback.model.entity.User;
import com.dg.f1fantasyback.model.entity.UserTeam;
import com.dg.f1fantasyback.repository.UserTeamRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;
    private final UserTeamMapper userTeamMapper;

    public UserTeamService(UserTeamRepository userTeamRepository,
                           UserTeamMapper userTeamMapper) {
        this.userTeamRepository = userTeamRepository;
        this.userTeamMapper = userTeamMapper;
    }

    public Iterable<UserTeamDetailDto> getTeams() {
        return userTeamRepository.findAll().stream().map(userTeamMapper::toDetailDto).collect(Collectors.toList());
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
        return userTeamMapper.toDetailDto(userTeamRepository.save(userTeamMapper.partialUpdate(userTeamCreateDto, userTeamRepository.findById(id).orElseThrow())));
    }

    public void deleteTeam(Long id) {
        userTeamRepository.deleteById(id);
    }
}
