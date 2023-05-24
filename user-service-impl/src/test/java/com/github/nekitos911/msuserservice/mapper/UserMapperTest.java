package com.github.nekitos911.msuserservice.mapper;

import com.github.nekitos911.msuserservice.TestDataFactory;
import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {
    @SuppressWarnings("all")
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testMapDtoToEntity() {
        UserDto user = TestDataFactory.createUser();

        UserEntity entity = userMapper.convert(user);

        assertThat(entity)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("id").build())
                .isEqualTo(user);
    }

    @Test
    void testMapEntityToDto() {
        UserEntity user = TestDataFactory.createUserEntity();

        UserDto entity = userMapper.convert(user);

        assertThat(entity)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("id").build())
                .isEqualTo(user);
    }

    @Test
    void testMapListEntityToDto() {
        List<UserEntity> users = Arrays.asList(TestDataFactory.createUserEntity(), TestDataFactory.createUserEntity());

        List<UserDto> entities = userMapper.convertListOfEntities(users);

        assertThat(entities)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("id").build())
                .isEqualTo(users);
    }

    @Test
    void testMapListDtoToEntity() {
        List<UserDto> users = Arrays.asList(TestDataFactory.createUser(), TestDataFactory.createUser());

        List<UserEntity> entities = userMapper.convertListOfDtos(users);

        assertThat(entities)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("id").build())
                .isEqualTo(users);
    }

}