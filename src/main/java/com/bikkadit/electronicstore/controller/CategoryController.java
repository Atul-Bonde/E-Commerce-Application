package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.constants.UrlConstant;
import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.payloads.ApiResponseMessage;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstant.BASE_URL + UrlConstant.CATEGORY_URL)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){

        this.categoryService.deleteCategory(categoryId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETED).status(true).httpStatus(HttpStatus.OK).build();

        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

}
