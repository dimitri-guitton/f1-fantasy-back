package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.exception.UniqueConstraintException;
import com.dg.f1fantasyback.mapper.UserMapper;
import com.dg.f1fantasyback.model.dto.user.UserCreateDto;
import com.dg.f1fantasyback.model.dto.user.UserDetailDto;
import com.dg.f1fantasyback.model.dto.user.UserUpdateDto;
import com.dg.f1fantasyback.model.entity.User;
import com.dg.f1fantasyback.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    private boolean existsByIdAndUsername(UUID id, String username) {
        return userRepository.existsByIdAndUsername(id, username);
    }

    public Iterable<UserDetailDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toDetailDto).collect(Collectors.toList());
    }

    public UserDetailDto getUser(UUID id) {
        return userMapper.toDetailDto(userRepository.findById(id).orElseThrow());
    }

    public UserDetailDto createUser(UserCreateDto userCreateDto) {
        if (existsByUsername(userCreateDto.getUsername())) {
            throw new UniqueConstraintException("Le nom d'utilisateur est déjà utilisé");
        }

        User user = userMapper.toEntity(userCreateDto);
        return userMapper.toDetailDto(userRepository.save(user));
    }

    public UserDetailDto updateUser(UUID id, UserUpdateDto userUpdateDto) {
        if (!existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (existsByUsername(userUpdateDto.getUsername()) && !existsByIdAndUsername(id, userUpdateDto.getUsername())) {
            throw new UniqueConstraintException("Le nom d'utilisateur est déjà utilisé");
        }

        User user = userRepository.findById(id).orElseThrow();
        user = userMapper.partialUpdate(userUpdateDto, user);
        return userMapper.toDetailDto(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        if (!existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
    }
}
