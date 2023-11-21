package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.constants.UrlConstant;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.payloads.ApiResponseMessage;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(UrlConstant.BASE_URL+UrlConstant.USERS_URL)
public class UserController {

    @Autowired
    private UserService userService;

    // create

    /**
     * @apiNote  save user data
     * @author Atul
     * @param userDto
     * @return
     * @since 1.0v
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {

        log.info("Entering request for save user data");

        UserDto userDto1 = this.userService.saveUser(userDto);

        log.info("Completed request for save user data");

        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }


    // update

    /**
     * @author Atul
     * @param userDto
     * @param userId
     * @return
     * @apiNote update user data
     * @since 1.0v
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

        log.info("Entering the request for update  user data with userId : {} ", userId);

        UserDto userDto1 = this.userService.updateUser(userDto, userId);

        log.info("Completed the request for update  user data with userId : {} ", userId);

        return new ResponseEntity<>(userDto1, HttpStatus.OK);

    }


    //delete

    /**
     * @author Atul
     * @param userId
     * @return
     * @apiNote Delete user data
     * @since 1.0v
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {

        log.info("Entering the request for delete  user data with userId : {} ", userId);

        this.userService.deleteUser(userId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETED + userId).status(false).httpStatus(HttpStatus.OK).build();

        log.info("Completed the request for update  user data with userId : {} ", userId);

        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    // single

    /**
     * @author Atul
     * @param userId
     * @return UserDto
     * @since 1.0v
     * @apiNote get user by userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {

        log.info("Entering the request for get user data with userId : {} ", userId);

        UserDto userDto = this.userService.getSingleUser(userId);

        log.info("Completed the request for get user data with userId : {} ", userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //get All

    /**
     * @autthor Atul
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param sortDirection
     * @return
     * @apiNote Get all users data
     * @since 1.0v
     */

    @GetMapping()
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value = "pageSize", defaultValue = UrlConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(value = "pageNumber", defaultValue = UrlConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(value = "sortBy", defaultValue = UrlConstant.SORT_BY, required = false) String sortBy,
                                                                 @RequestParam(value = "sortDirection", defaultValue = UrlConstant.SORT_DIRECTION, required = false) String sortDirection) {

        log.info("Entering the request for get all user date");

        PageableResponse response = this.userService.getAll(pageNumber, pageSize, sortBy, sortDirection);

        log.info("Completed the request for get all user date");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //find by email

    /**
     * @author Atul
     * @param email
     * @return
     * @apiNote get user by user Email
     * @since 1.0v
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {

        log.info("Entering the request for get the user data with userEmail : {}", email);

        UserDto userDto = this.userService.getByEmail(email);

        log.info("Completed the request for get the user data with userEmail : {}", email);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //search

    /**
     * @author Atul
     * @param keyword
     * @return
     * @apiNote serach user by keyword
     * @since 1.0v
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        log.info("Entering the request for search the user data with keyword : {}", keyword);

        List<UserDto> userDtos = this.userService.searchUser(keyword);

        log.info("Completed the request for search the user data with keyword : {}", keyword);

        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

}
