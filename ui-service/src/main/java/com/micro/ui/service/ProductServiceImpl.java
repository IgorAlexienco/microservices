package com.micro.ui.service;

import com.micro.ui.domain.Product;
import com.micro.ui.repositories.ProductRepository;
import com.micro.ui.web.controller.NotFoundException;
import com.micro.ui.web.mappers.ProductMapper;
import com.micro.ui.web.model.ProductDto;
import com.micro.ui.web.model.ProductPagedList;
import com.micro.ui.web.model.ProductEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

import com.micro.ui.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Cacheable(cacheNames = "productListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductPagedList listProducts(String productName, ProductEnum productType, PageRequest pageRequest, Boolean showInventoryOnHand) {
        log.debug("Getting product list for name: " + productName + " product type " + productType);

        ProductPagedList productPagedList;
        Page<Product> productPage;

        if (!StringUtils.isEmpty(productName) && !StringUtils.isEmpty(productType)) {
            //search both
            productPage = productRepository.findAllByProductNameAndProductType(productName, productType, pageRequest);
        } else if (!StringUtils.isEmpty(productName) && StringUtils.isEmpty(productType)) {
            //search product_service name
            productPage = productRepository.findAllByProductName(productName, pageRequest);
        } else if (StringUtils.isEmpty(productName) && !StringUtils.isEmpty(productType)) {
            //search product_service style
            productPage = productRepository.findAllByProductType(productType, pageRequest);
        } else {
            productPage = productRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            productPagedList = new ProductPagedList(productPage
                    .getContent()
                    .stream()
                    .map(productMapper::productToProductDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(productPage.getPageable().getPageNumber(),
                                    productPage.getPageable().getPageSize()),
                    productPage.getTotalElements());
        } else {
            productPagedList = new ProductPagedList(productPage
                    .getContent()
                    .stream()
                    .map(productMapper::productToProductDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(productPage.getPageable().getPageNumber(),
                                    productPage.getPageable().getPageSize()),
                    productPage.getTotalElements());
        }

        return productPagedList;
    }

    @Cacheable(cacheNames = "productCache", key = "#productId", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductDto getById(UUID productId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return productMapper.productToProductDtoWithInventory(
                    productRepository.findById(productId).orElseThrow(NotFoundException::new)
            );
        } else {
            return productMapper.productToProductDto(
                    productRepository.findById(productId).orElseThrow(NotFoundException::new)
            );
        }
    }

    @Override
    public ProductDto saveNewProduct(ProductDto productDto) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(productDto)));
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);

        product.setProductName(productDto.getProductName());
        product.setProductType(ProductEnum.PET.valueOf(productDto.getProductType()));
        product.setPrice(productDto.getPrice());
        product.setUpc(productDto.getUpc());

        return productMapper.productToProductDto(productRepository.save(product));
    }

    @Cacheable(cacheNames = "productUpcCache")
    @Override
    public ProductDto getByUpc(String upc) {
        return productMapper.productToProductDto(productRepository.findByUpc(upc));
    }

    @Override
    public void deleteProductById(UUID productId) {
        productRepository.deleteById(productId);
    }
}
