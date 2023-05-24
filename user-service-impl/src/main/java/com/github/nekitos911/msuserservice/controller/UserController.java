package com.github.nekitos911.msuserservice.controller;


import com.github.nekitos911.msuserservice.api.UserApi;
import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;
import com.github.nekitos911.msuserservice.enums.Source;
import com.github.nekitos911.msuserservice.model.GenericResponse;
import com.github.nekitos911.msuserservice.service.UserRepositoryService;
import com.github.nekitos911.msuserservice.validator.SourceHeaderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {
    private final SourceHeaderValidator sourceHeaderValidator;
    private final UserRepositoryService userRepositoryService;

    @Override
    public ResponseEntity<GenericResponse<Long>> createUser(UserDto dto, Source xSource) {
        sourceHeaderValidator.validate(xSource, dto);

        long userId = userRepositoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.ofResult(userId));
    }

    @Override
    public ResponseEntity<GenericResponse<UserDto>> getUserById(Long id) {
        UserDto userDto = userRepositoryService.getById(id);
        return ResponseEntity.ok(GenericResponse.ofResult(userDto));
    }

    @Override
    public ResponseEntity<GenericResponse<List<UserDto>>> searchUser(UserSearchRequestDto searchRequestDto) {
        return ResponseEntity.ok(GenericResponse.ofResult(userRepositoryService.search(searchRequestDto)));
    }
}
