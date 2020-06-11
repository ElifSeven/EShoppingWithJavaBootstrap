package com.shoppingproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingproject.domain.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
	
    Product save(Product product);
    Optional<Product> findById(Long id);

}
