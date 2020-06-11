package com.shoppingadmin.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingadmin.domain.Customer;

public interface UserRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);
}
