package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.payloads.PageableResponse;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(String productId,ProductDto productDto);

    ProductDto getProduct(String productId);

    PageableResponse<ProductDto> getAllProduct(Integer pageSize,Integer pageNumber,String sortBy,String sortDirection);

    void deleteProduct(String productId);

    PageableResponse<ProductDto> serachByTitle(Integer pageSize,Integer pageNumber,String sortBy,String sortDirection,String keyword );

}
