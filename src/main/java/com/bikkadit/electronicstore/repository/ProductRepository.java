package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product> findByTitleContaining(String keyword, Pageable pageable);
}
