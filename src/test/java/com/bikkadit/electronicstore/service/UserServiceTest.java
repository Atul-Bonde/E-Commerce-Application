package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Optional;

@SpringBootTest(classes = {UserServiceTest.class})
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void inti() {

        user = User.builder().userId("userid").gender("male").name("atul").about("I am Software Engineer").
                imageName("abc.png").password("ab@1616").email("ab@gmail.com").build();


    }

    @Test
    public void saveUserTest() {

        UserDto userDto = UserDto.builder().userId("userid").gender("male").name("atul").about("I am Software Engineer").
                imageName("abc.png").password("ab@1616").email("ab@gmail.com").build();

        Mockito.when(this.modelMapper.map(userDto,User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        UserDto userDto1 = this.userServiceImpl.saveUser(userDto);

        Assertions.assertNotNull(userDto1);

        Assertions.assertEquals("userid",userDto1.getUserId());


    }

    @Test
    public void updateUserTest(){

        UserDto userDto = UserDto.builder().userId("userid").gender("male").name("amol").about("I am Tester").
                imageName("am.png").password("am@1616").email("am@gmail.com").build();

        Mockito.when(this.modelMapper.map(userDto,User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        Mockito.when(this.userRepository.findById("userid")).thenReturn(Optional.of(user));

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        UserDto actualResult = this.userServiceImpl.updateUser(userDto, "userid");

        Assertions.assertNotNull(actualResult);

        Assertions.assertEquals("amol",actualResult.getName());

    }





}
