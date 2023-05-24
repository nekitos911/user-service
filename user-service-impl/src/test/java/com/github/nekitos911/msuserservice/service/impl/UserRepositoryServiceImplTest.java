package com.github.nekitos911.msuserservice.service.impl;

import com.github.nekitos911.msuserservice.TestDataFactory;
import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;
import com.github.nekitos911.msuserservice.exception.EmptySearchParamsException;
import com.github.nekitos911.msuserservice.exception.UserNotFoundException;
import com.github.nekitos911.msuserservice.mapper.UserMapper;
import com.github.nekitos911.msuserservice.repository.UserRepository;
import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    @SuppressWarnings("all")
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserRepositoryServiceImpl userRepositoryService;

    @Test
    void testGetById() {
        long id = 1L;
        UserEntity expected = TestDataFactory.createUserEntity(id);
        when(userRepository.findById(eq(id))).thenReturn(Optional.of(expected));

        UserDto actual = userRepositoryService.getById(id);

        assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("id").build())
                .isEqualTo(expected);
    }

    @Test
    void testGetById_whenUserNotExists() {
        long id = 1L;
        when(userRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRepositoryService.getById(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testCreateUser() {
        long userId = 1L;
        UserDto user = TestDataFactory.createUser();
        UserEntity userEntity = userMapper.convert(user);
        userEntity.setId(userId);
        when(userRepository.save(any())).thenReturn(userEntity);

        Long actualId = userRepositoryService.create(user);

        verify(userRepository, times(1)).save(any());

        assertThat(actualId).isEqualTo(userId);
    }

    @ParameterizedTest
    @MethodSource("searchRequestsSuccess")
    void testSearchUser(UserSearchRequestDto userSearchRequestDto) {
        UserDto user = TestDataFactory.createUser();
        user.setEmail(userSearchRequestDto.getEmail());
        user.setFirstName(userSearchRequestDto.getFirstName());
        user.setLastName(userSearchRequestDto.getLastName());
        user.setMiddleName(userSearchRequestDto.getMiddleName());
        user.setPhone(userSearchRequestDto.getPhone());

        UserEntity userEntity = userMapper.convert(user);

        when(userRepository.search(any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(userEntity));

        List<UserDto> actual = userRepositoryService.search(userSearchRequestDto);

        assertThat(actual)
                .hasSize(1)
                .asList()
                .first()
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void testSearchUser_emptyRequestData() {
        UserSearchRequestDto userSearchRequestDto = new UserSearchRequestDto();

        assertThatThrownBy(() -> userRepositoryService.search(userSearchRequestDto))
                .isInstanceOf(EmptySearchParamsException.class);
    }

    @Test
    void testSearchUser_emptyResultData() {
        UserSearchRequestDto userSearchRequestDto = TestDataFactory.createUserSearchRequestDto(TestDataFactory.createUser());

        assertThatThrownBy(() -> userRepositoryService.search(userSearchRequestDto))
                .isInstanceOf(UserNotFoundException.class);
    }

    private static Stream<Arguments> searchRequestsSuccess() {
        return Stream.of(
                Arguments.of(UserSearchRequestDto.builder().phone("71111111111").lastName("test").build()),
                Arguments.of(UserSearchRequestDto.builder().phone("71111111111").lastName("test").firstName("test").build()),
                Arguments.of(UserSearchRequestDto.builder().phone("71111111111").lastName("test").firstName("test").middleName("test").build()),
                Arguments.of(UserSearchRequestDto.builder().phone("71111111111").lastName("test").firstName("test").middleName("test").email("test@test.ru").build()),
                Arguments.of(UserSearchRequestDto.builder().phone("71111111111").build()),
                Arguments.of(UserSearchRequestDto.builder().middleName("test").build()),
                Arguments.of(UserSearchRequestDto.builder().firstName("test").build()),
                Arguments.of(UserSearchRequestDto.builder().lastName("test").build()),
                Arguments.of(UserSearchRequestDto.builder().email("test@test.ru").build())
        );
    }

}