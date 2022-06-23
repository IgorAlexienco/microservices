package com.micro.ui.web.mappers;

import com.micro.ui.domain.Customer;
import com.micro.ui.web.model.CustomerDto;
import com.micro.ui.domain.Product;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer customer);
}
