package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.constants.UrlConstant;
import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.payloads.ApiResponseMessage;
import com.bikkadit.electronicstore.payloads.ImageResponse;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.service.CategoryService;
import com.bikkadit.electronicstore.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(UrlConstant.BASE_URL + UrlConstant.CATEGORY_URL)
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String path;

    /**
     * @author Atul Bonde
     * @param categoryDto
     * @return
     * @apiNote Save Category Data
     * @since 1.0v
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {

        log.info("Entering request for save category data");

        CategoryDto categoryDto1 = this.categoryService.saveCategory(categoryDto);

        log.info("Completed request for save category data");

        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }

    /**
     * @author Atul Bonde
     * @param categoryId
     * @return
     * @apiNote Get Single Category
     * @since 1.0v
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {

        log.info("Entering request for get category with categoryId : {} ",categoryId);

        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);

        log.info("Completed request for get category with categoryId : {} ",categoryId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    /**
     * @author Atul Bonde
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDirection
     * @return
     * @apiNote Get All Category Data
     */
    @GetMapping("")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = UrlConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = UrlConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = UrlConstant.SORT_BY_CATEGORY) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = UrlConstant.SORT_DIRECTION) String sortDirection
    ) {

        log.info("Entering request for get all category data");

        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDirection);

        log.info("Completed request for get all category data");

        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    /**
     * @author Atul Bonde
     * @param categoryId
     * @param categoryDto
     * @return
     * @apiNote Update Category Data
     * @since 1.0v
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {

        log.info("Entering request for update category data with categoryId : {}",categoryId);

        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);

        log.info("Completed request for update category data with categoryId : {}",categoryId);

        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);

    }

    /**
     * @author Atul Bonde
     * @param categoryId
     * @return
     * @apiNote Delete Category Data
     * @since 1.0v
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {

        log.info("Entering request for delete category data with catergoryId :{}",categoryId);

        this.categoryService.deleteCategory(categoryId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETED).status(true).httpStatus(HttpStatus.OK).build();

        log.info("Completed request for delete category data with catergoryId :{}",categoryId);

        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    /**
     * @author Atul Bonde
     * @param file
     * @param categoryId
     * @return
     * @throws IOException
     * @apiNote Upload Cover Image for Category
     * @since 1.0v
     */
    @PostMapping("/images/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam MultipartFile file,@PathVariable String categoryId) throws IOException {

        log.info("Entering request for upload coverImage for category with categoryId :{}",categoryId);

        String ImageName = this.fileService.uploadFile(file, path);

        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);

        categoryDto.setCoverImage(ImageName);

        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().message("Cover Image Uploaded Successfully..").imageName(ImageName).status(true).httpStatus(HttpStatus.OK).build();

        log.info("Completed request for upload coverImage for category with categoryId :{}",categoryId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.OK);

    }

    /**
     * @author Atul Bonde
     * @param categoryId
     * @param response
     * @throws IOException
     * @since 1.0v
     * @apiNote Serve Cover Image of Category
     */
    @GetMapping("/images/{categoryId}")
    public void serveCoverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        log.info("Entering request for get coverImage for category with catergoryId :{}",categoryId);

        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);

        InputStream resource = fileService.getResource(path, categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

        log.info("Completed request for get coverImage for category with catergoryId :{}",categoryId);

    }
}
