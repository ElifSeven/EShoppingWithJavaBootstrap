package com.shoppingadmin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingadmin.domain.Product;
import com.shoppingadmin.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return  (List<Product>) productRepository.findAll();
	}


	@Override
	public Optional<Product> findById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}






	

	
	

}
