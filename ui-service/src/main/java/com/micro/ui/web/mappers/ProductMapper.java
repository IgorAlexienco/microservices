package com.micro.ui.web.mappers;

import com.micro.ui.domain.Product;
import com.micro.ui.web.model.ProductDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    ProductDto productToProductDtoWithInventory(Product product);

    Product productDtoToProduct(ProductDto dto);

}
