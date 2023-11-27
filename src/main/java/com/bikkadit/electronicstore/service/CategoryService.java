package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.payloads.PageableResponse;

public interface CategoryService {

  public CategoryDto saveCategory(CategoryDto categoryDto);

  public CategoryDto getSingleCategory(String categoryId);

  public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);

  public CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

  public void deleteCategory(String categoryId);


}
