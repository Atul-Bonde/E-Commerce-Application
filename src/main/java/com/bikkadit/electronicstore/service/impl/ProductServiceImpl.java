package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constants.AppConstant;
import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.entities.Product;
import com.bikkadit.electronicstore.exception.ResourceNotFound;
import com.bikkadit.electronicstore.payloads.Helper;
import com.bikkadit.electronicstore.payloads.PageableResponse;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = this.modelMapper.map(productDto, Product.class);

        String productId = UUID.randomUUID().toString();

        product.setProductId(productId);

        Product saveProduct = this.productRepository.save(product);

        ProductDto productDto1 = this.modelMapper.map(saveProduct, ProductDto.class);

        return productDto1;
    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {

        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + productId));

        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(new Date());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.isStock());
        product.setLive(product.isLive());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setTitle(productDto.getTitle());

        Product save = this.productRepository.save(product);

        ProductDto productDto1 = this.modelMapper.map(save, ProductDto.class);

        return productDto1;
    }

    @Override
    public ProductDto getProduct(String productId) {

        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + productId));

        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);

        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageSize, Integer pageNumber, String sortBy, String sortDirection) {

        Sort sort;

        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = this.productRepository.findAll(pageRequest);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(productPage, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + productId));

        this.productRepository.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> serachByTitle(Integer pageSize, Integer pageNumber, String sortBy, String sortDirection, String keyword) {

        Sort sort;

        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> byTitleContaining = this.productRepository.findByTitleContaining(keyword, pageRequest);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byTitleContaining, ProductDto.class);

        return pageableResponse;
    }
}
