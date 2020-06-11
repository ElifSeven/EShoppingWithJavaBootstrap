package com.shoppingadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shoppingadmin.domain.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{

	Product save(Product product);

	List<Product> findAll();
	
	 Optional<Product> findById(Long id);
	

}
