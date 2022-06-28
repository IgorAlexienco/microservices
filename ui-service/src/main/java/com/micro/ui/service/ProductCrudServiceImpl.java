package com.micro.ui.service;

import com.micro.ui.domain.Product;
import com.micro.ui.repositories.ProductCrudRepository;
import com.micro.ui.repositories.ProductRepository;
import com.micro.ui.web.controller.NotFoundException;
import com.micro.ui.web.mappers.ProductMapper;
import com.micro.ui.web.model.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCrudServiceImpl implements ProductCrudService {

    private final ProductCrudRepository productCrudRepository;

    private final ProductMapper productMapper;

    @Cacheable(cacheNames = "productCrudCache", key = "#productId", condition = "#showInventoryOnHand == false ")
    @Override
    public Product getById(UUID productId) {
        log.debug("Calling getById");
        return productCrudRepository.findById(productId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<ProductDto> findAll(){
        log.debug("Calling find All");

        List<Product> productLst= (List<Product>) productCrudRepository.findAll();
        List<ProductDto> productDtoLst= productLst.stream().map(productMapper::productToProductDto).collect(Collectors.toList());

        return productDtoLst;

    }

}
//
// productPagedList = new ProductPagedList(productPage
//         .getContent()
//         .stream()
//         .map(productMapper::productToProductDto)
//         .collect(Collectors.toList()),
//         PageRequest
//         .of(productPage.getPageable().getPageNumber(),
//         productPage.getPageable().getPageSize()),
//         productPage.getTotalElements());