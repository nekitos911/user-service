package com.github.nekitos911.msuserservice.mapper;

import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto convert(UserEntity userEntity);
    @Mapping(target = "id", ignore = true)
    UserEntity convert(UserDto userEntity);

    List<UserDto> convertListOfEntities(List<UserEntity> userEntity);
    List<UserEntity> convertListOfDtos(List<UserDto> userEntity);
}
