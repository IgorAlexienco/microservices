package com.micro.ui.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.ui.bootstrap.ProductLoader;
import com.micro.ui.service.ProductService;
import com.micro.ui.web.controller.ProductController;
import com.micro.ui.web.model.ProductDto;
import com.micro.ui.web.model.ProductEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ProductDto validProduct;

    @BeforeEach
    public void setUp() {
        validProduct = ProductDto.builder().id(UUID.randomUUID())
                .productName("Apple Red")
                .productType(ProductEnum.FROOT.toString())
                .upc(ProductLoader.FROOT_1_UPC)
                .build();
    }

    @Test
    public void getFroot() throws Exception {
        given(productService.getById(any(UUID.class), any())).willReturn(validProduct);

        mockMvc.perform(get("/api/v1/product/" + validProduct.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validProduct.getId().toString())))
                .andExpect(jsonPath("$.productName", is("Apple Red")));
    }

    @Test
    public void handlePost() throws Exception {
        //given
        ProductDto productDto = validProduct;
        productDto.setId(null);
        ProductDto savedDto = ProductDto.builder().id(UUID.randomUUID()).productName("New Froot").build();
        String productDtoJson = objectMapper.writeValueAsString(productDto);

        given(productService.saveNewProduct(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void handleUpdate() throws Exception {
        //given
        ProductDto productDto = validProduct;
        productDto.setId(null);
        String productDtoJson = objectMapper.writeValueAsString(productDto);

        //when
        mockMvc.perform(put("/api/v1/product/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(status().isNoContent());

        then(productService).should().updateProduct(any(), any());

    }
}
