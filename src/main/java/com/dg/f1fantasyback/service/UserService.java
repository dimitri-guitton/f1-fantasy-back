package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.exception.UniqueConstraintException;
import com.dg.f1fantasyback.mapper.UserMapper;
import com.dg.f1fantasyback.model.dto.app_user.UserCreateDto;
import com.dg.f1fantasyback.model.dto.app_user.UserDetailDto;
import com.dg.f1fantasyback.model.dto.app_user.UserUpdateDto;
import com.dg.f1fantasyback.model.entity.AppUser;
import com.dg.f1fantasyback.model.enums.Role;
import com.dg.f1fantasyback.repository.UserRepository;
import com.dg.f1fantasyback.security.JWTService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

        AppUser appUser = userMapper.toEntity(userCreateDto);
        appUser.setPassword(passwordEncoder.encode(userCreateDto.getPassword())); // Hash du mot de passe
        appUser.setEnabled(true);
        appUser.setRole(Role.ROLE_USER);
        return userMapper.toDetailDto(userRepository.save(appUser));
    }

    public UserDetailDto updateUser(UUID id, UserUpdateDto userUpdateDto) {
        return userRepository.findById(id).map(existingUser -> {
            if (existsByUsername(userUpdateDto.getUsername()) && !existsByIdAndUsername(id, userUpdateDto.getUsername())) {
                throw new UniqueConstraintException("Le nom d'utilisateur est déjà utilisé");
            }
            userMapper.partialUpdate(userUpdateDto, existingUser);

            // Si un mot de passe est fourni, on l'encode avant de l'enregistrer
            if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
            }

            return userMapper.toDetailDto(userRepository.save(existingUser));
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public void deleteUser(UUID id) {
        if (!existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
    }

    public String register(@Valid UserCreateDto userCreateDto) {
        UserDetailDto user = createUser(userCreateDto);

        return jwtService.generateToken(user);
    }
}
