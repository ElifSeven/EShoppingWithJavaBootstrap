package com.shoppingproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingproject.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByName(String name);

}
