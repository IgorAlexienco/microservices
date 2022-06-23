package com.micro.ui.service;

import com.micro.ui.web.model.ProductEnum;
import com.micro.ui.web.model.ProductPagedList;
import com.micro.ui.web.model.ProductDto;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ProductService {

    ProductPagedList listProducts(String productName, ProductEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    ProductDto getById(UUID productId, Boolean showInventoryOnHand);

    ProductDto saveNewProduct(ProductDto productDto);

    ProductDto updateProduct(UUID productId, ProductDto productDto);

    ProductDto getByUpc(String upc);

    void deleteProductById(UUID beerId);
}
