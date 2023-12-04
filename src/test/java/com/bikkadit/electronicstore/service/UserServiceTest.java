package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.payloads.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.List;
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

        Mockito.when(this.modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        UserDto userDto1 = this.userServiceImpl.saveUser(userDto);

        Assertions.assertNotNull(userDto1);

        Assertions.assertEquals("userid", userDto1.getUserId());


    }

    @Test
    public void updateUserTest() {

        UserDto userDto = UserDto.builder().userId("userid").gender("male").name("amol").about("I am Tester").
                imageName("am.png").password("am@1616").email("am@gmail.com").build();

        Mockito.when(this.modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        Mockito.when(this.userRepository.findById("userid")).thenReturn(Optional.of(user));

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        UserDto actualResult = this.userServiceImpl.updateUser(userDto, "userid");

        Assertions.assertNotNull(actualResult);

        Assertions.assertEquals("amol", actualResult.getName());

    }

    @Test
    public void getSingleUserTest() {

        UserDto userDto = UserDto.builder().userId("userid").gender("male").name("atul").about("I am Software Engineer").
                imageName("abc.png").password("ab@1616").email("ab@gmail.com").build();

        Mockito.when(this.modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        Mockito.when(this.userRepository.findById("userid")).thenReturn(Optional.of(user));

        UserDto actualResult = this.userServiceImpl.getSingleUser("userid");

        Assertions.assertNotNull(actualResult);

        Assertions.assertEquals("atul", actualResult.getName());

    }

    @Test
    public void deleteUserTest() {


        String userid = "userid";

        Mockito.when(this.userRepository.findById(userid)).thenReturn(Optional.of(user));

        this.userServiceImpl.deleteUser(userid);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);


    }


    @Test
    public void getUserByEmailTest() {

        String email = "ab@gmail.com";

        UserDto userDto = UserDto.builder().userId("userid").gender("male").name("atul").about("I am Software Engineer").
                imageName("abc.png").password("ab@1616").email("ab@gmail.com").build();

        Mockito.when(this.modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto actualResult = this.userServiceImpl.getByEmail(email);

        Assertions.assertNotNull(actualResult);

        Assertions.assertEquals("atul",actualResult.getName());
        Assertions.assertEquals("ab@gmail.com",actualResult.getEmail());


    }

    @Test
    public void searchUserByKeywordsTest(){

        User user1 = User.builder().userId("userid").gender("male").name("atul").about("I am Software Engineer").
                imageName("abc.png").password("ab@1616").email("ab@gmail.com").build();

        User user2 = User.builder().userId("userid1").gender("male").name("amit").about("I am Software Engineer").
                imageName("abcd.png").password("am@1616").email("am@gmail.com").build();

        User user3 = User.builder().userId("userid2").gender("male").name("amol").about("I am Software Engineer").
                imageName("iop.png").password("amol@1891").email("amol@gmail.com").build();


        List<User> list_user = List.of(user1, user2, user3);

        Mockito.when(this.userRepository.findByNameContaining("a")).thenReturn(list_user);

        List<UserDto> list_userDto = this.userServiceImpl.searchUser("a");

        Assertions.assertEquals(3,list_userDto.size());


    }

    @Test
    public void getAllUsersTest(){

        User user1 = User.builder().userId("userid").gender("male").name("atul").about("I am Software Engineer").
                imageName("abc.png").password("ab@1616").email("ab@gmail.com").build();

        User user2 = User.builder().userId("userid1").gender("male").name("amit").about("I am Software Engineer").
                imageName("abcd.png").password("am@1616").email("am@gmail.com").build();

        User user3 = User.builder().userId("userid2").gender("male").name("amol").about("I am Software Engineer").
                imageName("iop.png").password("amol@1891").email("amol@gmail.com").build();


        List<User> list_user = List.of(user1, user2, user3);

        Page<User> page=new PageImpl<>(list_user);

        Mockito.when(this.userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUsers = this.userServiceImpl.getAll(1, 3, "name", "asc");

        Assertions.assertEquals(3,allUsers.getContent().size());

    }


}
