package com.micro.ui.repositories;

import com.micro.ui.bootstrap.ProductLoader;
import com.micro.ui.domain.Product;
import com.micro.ui.repositories.ProductCrudRepository;

import com.micro.ui.service.ProductCrudService;
import com.micro.ui.service.ProductCrudServiceImpl;
import com.micro.ui.web.controller.NotFoundException;
import com.micro.ui.web.mappers.ProductMapper;
import com.micro.ui.web.model.ProductDto;
import com.micro.ui.web.model.ProductEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.UUID;

@Slf4j
public class TestProductCrudRepository {

    //@MockBean
    //ProductCrudRepository productCrudRepositoryMock;
    //ProductMapper productMapperMock;
    //@Autowired
    //ProductCrudServiceImpl productCrudService;

    //ProductDto productDto;

//    @BeforeEach
//    public void setUp() {
//        productCrudRepositoryMock=mock(ProductCrudRepository.class);
//        productMapperMock= mock(ProductMapper.class);
//        productCrudService= new ProductCrudServiceImpl(productCrudRepositoryMock, productMapperMock);

//        productSetUp = Product.builder().id(UUID.randomUUID())
//                .version(new Long(1234))
//                .productName("Apple Red")
//                .productType(ProductEnum.FROOT)
//                .upc(ProductLoader.FROOT_1_UPC)
//                .quantityOnHand(15)
//                .price(new BigDecimal(3.55))
//                .createdDate(new Timestamp(1234))
//                .lastModifiedDate(new Timestamp(5678))
//                .build();
//    }

    @DisplayName("Test find all")
    @Test
    void getAllTest() {
      ProductCrudRepository productCrudRepositoryMock=mock(ProductCrudRepository.class);
      ProductMapper productMapperMock= mock(ProductMapper.class);
      ProductCrudServiceImpl productCrudService= new ProductCrudServiceImpl(productCrudRepositoryMock, productMapperMock);
      List<ProductDto> ptoductDtoLst=  productCrudService.findAll();
      when(productCrudService.findAll()).thenReturn(Arrays.asList(ProductDto.builder().build()));
      assertThat(ptoductDtoLst.size() == 1);
      log.info("find all test is compleated");
    }


    @DisplayName("Find By UUID with Exception")
    @Test()
    void getById() {
        ProductCrudRepository productCrudRepositoryMock=mock(ProductCrudRepository.class);
        ProductMapper productMapperMock= mock(ProductMapper.class);
        ProductCrudServiceImpl productCrudService= new ProductCrudServiceImpl(productCrudRepositoryMock, productMapperMock);
       try {
           Product product = productCrudService.getById(UUID.randomUUID());
           //when(productCrudService.getById(UUID.randomUUID())).thenThrow(new RuntimeException());
           given(productCrudService.getById(UUID.randomUUID())).willReturn(Product.builder().build());
           assertThat(product).isNotNull();
       }catch(NotFoundException nfe){
           log.error("Not Found Product fot UID");
       }
    }

}
