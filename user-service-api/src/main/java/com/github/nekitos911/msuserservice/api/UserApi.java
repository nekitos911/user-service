package com.github.nekitos911.msuserservice.api;

import com.github.nekitos911.msuserservice.contants.Constants;
import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;
import com.github.nekitos911.msuserservice.enums.Source;
import com.github.nekitos911.msuserservice.model.GenericResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/users")
@Validated
public interface UserApi {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse<Long>> createUser(@RequestBody @Valid UserDto dto, @RequestHeader(name = Constants.X_SOURCE_HEADER) Source xSource);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse<UserDto>> getUserById(@PathVariable Long id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse<List<UserDto>>> searchUser(@RequestBody UserSearchRequestDto searchRequestDto);
}
