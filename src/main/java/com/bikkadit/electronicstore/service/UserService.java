package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.payloads.PageableResponse;

import java.util.List;

public interface UserService {

    //create

    public UserDto saveUser(UserDto userDto);

    // update

    public UserDto updateUser(UserDto userDto,String userId);

    //get single user

    public UserDto getSingleUser(String userId);

    // get All Users

    public PageableResponse getAll(Integer pageNumber, Integer pageSize, String sortBy , String sortDirection);

    //delete

    public void deleteUser(String userId);

    //find by email

    public UserDto getByEmail(String email);

    // search

    public List<UserDto> searchUser(String keyword);

}
