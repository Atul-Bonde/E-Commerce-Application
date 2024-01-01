package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = {UserControllerTest.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    UserDto userDto;

    @BeforeEach
    public void init() {

        userDto = UserDto.builder().userId("userid").gender("male").name("Atul Bonde").about("I am Software Engineer").
                imageName("abc.png").password("Password@1616").email("ab@gmail.com").build();


    }

    @Test
    public void saveUserTest() throws Exception {

        Mockito.when(this.userService.saveUser(userDto)).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users/").
                contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonString(userDto))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    public String convertObjectToJsonString(Object userDto) throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(userDto);


    }


}
