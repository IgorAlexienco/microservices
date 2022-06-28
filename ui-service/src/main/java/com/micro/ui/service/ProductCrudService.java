package com.micro.ui.service;

import com.micro.ui.domain.Product;
import com.micro.ui.web.model.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductCrudService {

    Product getById(UUID productId);

    List<ProductDto> findAll();

}
