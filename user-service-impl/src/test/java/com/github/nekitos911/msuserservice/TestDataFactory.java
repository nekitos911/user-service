package com.github.nekitos911.msuserservice;

import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;
import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class TestDataFactory {
    public UserDto createUser() {
        return UserDto.builder()
                .bankId(1L)
                .birthDate(LocalDate.of(1990, 1, 1))
                .birthPlace("Moscow")
                .email("test@example.com")
                .phone("79999999999")
                .passportNumber("1234 123456")
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .middleName(UUID.randomUUID().toString())
                .build();
    }

    public UserSearchRequestDto createUserSearchRequestDto(UserDto user) {
        return Optional.ofNullable(user)
                .map(u -> UserSearchRequestDto.builder()
                        .phone(u.getPhone())
                        .email(u.getEmail())
                        .middleName(u.getMiddleName())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .build())
                .orElse(new UserSearchRequestDto());
    }

    public UserEntity createUserEntity() {
        return UserEntity.builder()
                .bankId(1L)
                .birthDate(LocalDate.of(1990, 1, 1))
                .birthPlace("Moscow")
                .email("test@example.com")
                .phone(UUID.randomUUID().toString())
                .passportNumber(UUID.randomUUID().toString())
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .middleName(UUID.randomUUID().toString())
                .build();
    }

    public UserEntity createUserEntity(Long id) {
        UserEntity userEntity = createUserEntity();
        userEntity.setId(id);
        return userEntity;
    }
}
