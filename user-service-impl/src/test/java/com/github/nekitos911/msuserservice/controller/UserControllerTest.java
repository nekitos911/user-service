package com.github.nekitos911.msuserservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nekitos911.msuserservice.TestDataFactory;
import com.github.nekitos911.msuserservice.config.TestContainerConfig;
import com.github.nekitos911.msuserservice.config.WebConfig;
import com.github.nekitos911.msuserservice.contants.Constants;
import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.dto.UserSearchRequestDto;
import com.github.nekitos911.msuserservice.enums.ErrorCode;
import com.github.nekitos911.msuserservice.enums.Source;
import com.github.nekitos911.msuserservice.mapper.UserMapper;
import com.github.nekitos911.msuserservice.repository.UserRepository;
import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestContainerConfig.class, WebConfig.class})
@ActiveProfiles("test")
class UserControllerTest {
    private final String API = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_success() throws Exception {
        UserDto user = TestDataFactory.createUser();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .header(Constants.X_SOURCE_HEADER, Source.MAIL)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_notValidEmail() throws Exception {
        UserDto user = TestDataFactory.createUser();
        user.setEmail("123");
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .header(Constants.X_SOURCE_HEADER, Source.MAIL)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.FIELDS_VALIDATION_ERROR.name()));
    }

    @Test
    void createUser_notValidPassport() throws Exception {
        UserDto user = TestDataFactory.createUser();
        user.setPassportNumber("123");
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .header(Constants.X_SOURCE_HEADER, Source.MAIL)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.FIELDS_VALIDATION_ERROR.name()));
    }

    @Test
    void getUser_success() throws Exception {
        UserEntity userEntity = userRepository.save(TestDataFactory.createUserEntity());

        UserDto user = userMapper.convert(userEntity);

        mockMvc.perform(get(API + "/{id}", userEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName").value(user.getFirstName()));
    }

    @Test
    void getUser_notFound() throws Exception {
        mockMvc.perform(get(API + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.USER_NOT_FOUND.name()));
    }

    @Test
    void findUser_success() throws Exception {
        UserDto user = TestDataFactory.createUser();

        UserSearchRequestDto userSearchRequestDto = TestDataFactory.createUserSearchRequestDto(user);
        String searchRequest = objectMapper.writeValueAsString(userSearchRequestDto);

        mockMvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.data[0].lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.data[0].middleName").value(user.getMiddleName()));
    }

    @Test
    void findUser_notFound() throws Exception {
        UserDto user = TestDataFactory.createUser();
        UserSearchRequestDto userSearchRequestDto = TestDataFactory.createUserSearchRequestDto(user);
        String searchRequest = objectMapper.writeValueAsString(userSearchRequestDto);

        mockMvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequest)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.USER_NOT_FOUND.name()));
    }

    @Test
    void findUser_emptyParameter() throws Exception {
        UserSearchRequestDto userSearchRequestDto = new UserSearchRequestDto();
        String searchRequest = objectMapper.writeValueAsString(userSearchRequestDto);

        mockMvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequest)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.EMPTY_SEARCH_PARAMS.name()));
    }

    @Test
    void findUser_notFundByParams() throws Exception {
        UserEntity userEntity = TestDataFactory.createUserEntity();
        userRepository.save(userEntity);

        UserSearchRequestDto userSearchRequestDto = new UserSearchRequestDto();
        userSearchRequestDto.setFirstName(UUID.randomUUID().toString());
        String searchRequest = objectMapper.writeValueAsString(userSearchRequestDto);

        mockMvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequest)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.USER_NOT_FOUND.name()));
    }

    @Test
    void findUser_ByName() throws Exception {
        UserEntity userEntity = TestDataFactory.createUserEntity();
        userRepository.save(userEntity);

        UserSearchRequestDto userSearchRequestDto = new UserSearchRequestDto();
        userSearchRequestDto.setFirstName(userEntity.getFirstName());
        String searchRequest = objectMapper.writeValueAsString(userSearchRequestDto);

        mockMvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].firstName").value(userEntity.getFirstName()))
                .andExpect(jsonPath("$.data[0].lastName").value(userEntity.getLastName()));
    }

    @Test
    void createUser_headerNotValid() throws Exception {
        UserDto user = TestDataFactory.createUser();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .header(Constants.X_SOURCE_HEADER, "wrong")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.description.errorCode").value(ErrorCode.SOURCE_HEADER_NOT_VALID.name()));
    }
}