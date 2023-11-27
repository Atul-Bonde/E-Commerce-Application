package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.constants.UrlConstant;
import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.payloads.ApiResponseMessage;
import com.bikkadit.electronicstore.payloads.ImageResponse;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.service.CategoryService;
import com.bikkadit.electronicstore.service.FileService;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String path;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {

        CategoryDto categoryDto1 = this.categoryService.saveCategory(categoryDto);

        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {

        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = UrlConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = UrlConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = UrlConstant.SORT_BY_CATEGORY) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = UrlConstant.SORT_DIRECTION) String sortDirection
    ) {
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDirection);

        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {

        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {

        this.categoryService.deleteCategory(categoryId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETED).status(true).httpStatus(HttpStatus.OK).build();

        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @PostMapping("/images/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam MultipartFile file,@PathVariable String categoryId) throws IOException {

        String ImageName = this.fileService.uploadFile(file, path);

        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);

        categoryDto.setCoverImage(ImageName);

        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().message("Cover Image Uploaded Successfully..").imageName(ImageName).status(true).httpStatus(HttpStatus.OK).build();

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.OK);

    }

    @GetMapping("/images/{categoryId}")
    public void serveCoverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);

        InputStream resource = fileService.getResource(path, categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

    }
}
