package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.entities.Category;
import com.bikkadit.electronicstore.exception.ResourceNotFound;
import com.bikkadit.electronicstore.payloads.Helper;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {

        log.info("Entering dao for save category data");

        Category category = this.modelMapper.map(categoryDto, Category.class);

        String categoryId = UUID.randomUUID().toString();

        category.setCategoryId(categoryId);

        Category saveCategory = this.categoryRepository.save(category);

        log.info("Completed dao for save category data");

        return this.modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {

        log.info("Entering dao category for get category data with categoryId : {}",categoryId);

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + categoryId));

        log.info("Completed dao category for get category data with categoryId : {}",categoryId);

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        log.info("Entering dao call for get all category data");

        Sort sort;

        if (sortDirection.equalsIgnoreCase("desc")) {

            sort = Sort.by(sortBy).descending();

        } else {
            sort = Sort.by(sortBy).ascending();
        }

        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> categoryPage = this.categoryRepository.findAll(page);

        PageableResponse<CategoryDto> CategoriesDto = Helper.getPageableResponse(categoryPage, CategoryDto.class);

        log.info("completed dao call for get all category data");

        return CategoriesDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        log.info("Entering dao call for update category data with categoryId : {}",categoryId);

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + categoryId));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updateCategory = this.categoryRepository.save(category);

        log.info("Completed dao call for update category data with categoryId : {}",categoryId);

        return this.modelMapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {

        log.info("Entering dao call for delete category data with categoryId : {}",categoryId);

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND));

        this.categoryRepository.delete(category);

        log.info("Completed dao call for delete category data with categoryId : {}",categoryId);
    }
}
