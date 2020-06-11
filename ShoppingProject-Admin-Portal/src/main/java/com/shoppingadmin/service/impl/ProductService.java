package com.shoppingadmin.service.impl;

import java.util.List;
import java.util.Optional;

import com.shoppingadmin.domain.Product;

public interface ProductService {
	Product save(Product product);

	List<Product> findAll();
	

	Optional<Product> findById(Long id);
	

}
