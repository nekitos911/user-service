package com.github.nekitos911.msuserservice.service;

import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;

import java.util.List;

public interface UserRepositoryService {
    Long create(UserDto user);
    UserDto getById(Long id);
    List<UserDto> search(UserSearchRequestDto searchRequestDto);
}
