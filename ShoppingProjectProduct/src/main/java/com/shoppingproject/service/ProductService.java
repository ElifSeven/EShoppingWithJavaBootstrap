package com.shoppingproject.service;

import java.util.List;
import java.util.Optional;

import com.shoppingproject.domain.Product;

public interface ProductService {

	List<Product> findAll();

	Optional<Product> findById(Long id);

}
