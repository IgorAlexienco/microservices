package com.micro.ui.web.controller;

import com.micro.ui.repositories.ProductCrudRepository;
import com.micro.ui.repositories.ProductRepository;
import com.micro.ui.service.ProductService;
import com.micro.ui.web.model.ProductDto;
import com.micro.ui.web.model.ProductPagedList;
import com.micro.ui.web.model.ProductEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class ProductController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private final ProductService productService;


    @GetMapping(produces = {"application/json"}, path = "product")
    public ResponseEntity<ProductPagedList> listProducts(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "productName", required = false) String productName,
                                                         @RequestParam(value = "productType", required = false) ProductEnum productType,
                                                         @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        ProductPagedList beerList = productService.listProducts(productName, productType, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") UUID productId,
                                                     @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        return new ResponseEntity<>(productService.getById(productId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("productUpc/{upc}")
    public ResponseEntity<ProductDto> getProductByUpc(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(productService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "product")
    public ResponseEntity saveNewProduct(@RequestBody @Validated ProductDto productDto) {

        ProductDto savedProduct = productService.saveNewProduct(productDto);

        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://api.microservices/api/v1/product/" + savedProduct.getId().toString())
                        .build().toUri())
                .build();
    }

    @PutMapping("product/{productId}")
    public ResponseEntity updateProductById(@PathVariable("productId") UUID productId, @RequestBody @Validated ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("product/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("productId") UUID productId) {

        productService.deleteProductById(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
