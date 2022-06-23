package com.micro.ui.repositories;

import com.micro.ui.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
// Long
public interface ProductCrudRepository extends CrudRepository<Product, UUID>{
}
