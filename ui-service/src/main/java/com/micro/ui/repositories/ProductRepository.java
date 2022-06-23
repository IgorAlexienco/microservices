package com.micro.ui.repositories;

import com.micro.ui.domain.Product;
import com.micro.ui.web.model.ProductEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>{

    Page<Product> findAllByProductName(String productName, Pageable pageable);

    Page<Product> findAllByProductType(ProductEnum productType, Pageable pageable);

    Page<Product> findAllByProductNameAndProductType(String productName, ProductEnum productType, Pageable pageable);

    Product findByUpc(String upc);
}
