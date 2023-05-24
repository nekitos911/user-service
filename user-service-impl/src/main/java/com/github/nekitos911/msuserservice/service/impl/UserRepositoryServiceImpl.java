package com.github.nekitos911.msuserservice.service.impl;

import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;
import com.github.nekitos911.msuserservice.exception.EmptySearchParamsException;
import com.github.nekitos911.msuserservice.exception.UserNotFoundException;
import com.github.nekitos911.msuserservice.mapper.UserMapper;
import com.github.nekitos911.msuserservice.repository.UserRepository;
import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import com.github.nekitos911.msuserservice.service.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryServiceImpl implements UserRepositoryService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long create(UserDto user) {
        return Optional.of(user).map(userMapper::convert)
                .map(userRepository::save)
                .map(UserEntity::getId)
                .orElseThrow(RuntimeException::new);
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::convert)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public List<UserDto> search(UserSearchRequestDto searchRequestDto) {
        if (ObjectUtils.allNull(searchRequestDto.getFirstName(),
                searchRequestDto.getMiddleName(),
                searchRequestDto.getLastName(),
                searchRequestDto.getPhone(),
                searchRequestDto.getEmail())) {
            throw new EmptySearchParamsException();
        }

        List<UserEntity> users = userRepository.search(searchRequestDto.getFirstName(),
                searchRequestDto.getLastName(),
                searchRequestDto.getMiddleName(),
                searchRequestDto.getPhone(),
                searchRequestDto.getEmail());

        if (users.isEmpty()) {
            throw new UserNotFoundException("Не найдено ни 1 пользователя по параметрам " + searchRequestDto);
        }

        return userMapper.convertListOfEntities(users);
    }
}
