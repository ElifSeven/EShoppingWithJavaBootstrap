package com.shoppingproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingproject.domain.Customer;

public interface UserRepository extends CrudRepository<Customer, Long> {

	Customer findByUsername(String username);
	
	Customer findByEmail(String email);

}
