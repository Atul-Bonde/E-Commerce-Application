package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.constants.UrlConstant;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.payloads.ApiResponseMessage;
import com.bikkadit.electronicstore.payloads.ImageResponse;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.service.FileService;
import com.bikkadit.electronicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(UrlConstant.BASE_URL + UrlConstant.USERS_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String path;

    // create

    /**
     * @param userDto
     * @return
     * @apiNote save user data
     * @author Atul
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
     * @param userDto
     * @param userId
     * @return
     * @author Atul
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
     * @param userId
     * @return
     * @author Atul
     * @apiNote Delete user data
     * @since 1.0v
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {

        log.info("Entering the request for delete  user data with userId : {} ", userId);

        this.userService.deleteUser(userId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETED + userId).status(true).httpStatus(HttpStatus.OK).build();

        log.info("Completed the request for update  user data with userId : {} ", userId);

        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    // single

    /**
     * @param userId
     * @return UserDto
     * @author Atul
     * @apiNote get user by userId
     * @since 1.0v
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
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param sortDirection
     * @return
     * @autthor Atul
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
     * @param email
     * @return
     * @author Atul
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
     * @param keyword
     * @return
     * @author Atul
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

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable String userId) throws IOException {

        log.info("Entering request for upload image for user with userId : {}",userId);

        String imageName = this.fileService.uploadFile(image, path);

        UserDto user = this.userService.getSingleUser(userId);

        user.setImageName(imageName);

        UserDto userDto = this.userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded Successfully..").imageName(imageName).status(true).httpStatus(HttpStatus.CREATED).build();

        log.info("Completed request for upload image for user with userId : {}",userId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void serveImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        log.info("Entering request for serve image of user with userId : {}",userId);

        UserDto userDto = userService.getSingleUser(userId);

        InputStream resource = fileService.getResource(path, userDto.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

        log.info("Entering request for serve image of user with userId : {}",userId);

    }
}
