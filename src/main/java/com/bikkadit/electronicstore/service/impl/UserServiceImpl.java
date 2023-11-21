package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.exception.ResourceNotFound;
import com.bikkadit.electronicstore.payloads.Helper;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {

        logger.info("Entering dao call for save the user data");

        User user = this.modelMapper.map(userDto, User.class);

        String str = UUID.randomUUID().toString();

        user.setUserId(str);

        User saveUser = this.userRepository.save(user);

        logger.info("Completed dao call for save the user data");

        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        logger.info("Entering dao call for update the user data with userId : {} ",userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + userId));
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updateUser = this.userRepository.save(user);

        logger.info("Completed dao call for update the user data with userId : {} ",userId);

        return this.modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public UserDto getSingleUser(String userId) {

        logger.info("Entering dao call for get the user data with userId : {} ",userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + userId));

        logger.info("Completed dao call for get the user data with userId : {} ",userId);

        return this.modelMapper.map(user, UserDto.class);

    }

    @Override
    public PageableResponse<UserDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        logger.info("Entering dao call for get all users data");

        Sort sort;

        if (sortDirection.equalsIgnoreCase("desc")) {

            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> userPages = this.userRepository.findAll(page);

        PageableResponse<UserDto> response = Helper.getPageableResponse(userPages, UserDto.class);

        logger.info("Completed dao call for get all users data");

        return response;
    }

    @Override
    public void deleteUser(String userId) {

        logger.info("Entering dao call for delete user data with userId {} : ",userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + userId));

        this.userRepository.delete(user);

        logger.info("Completed dao call for delete user data with userId {} : ",userId);
    }

    @Override
    public UserDto getByEmail(String email) {

        logger.info("Entering dao call for get user data with userEmail {} : ",email);

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + email));

        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        logger.info("Completed dao call for get user data with userEmail {} : ",email);

        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        logger.info("Entering dao call for search user data with keyword {} : ",keyword);

        List<User> users = this.userRepository.findByNameContaining(keyword);

        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        logger.info("Completed dao call for search user data with keyword {} : ",keyword);

        return userDtos;
    }
}
