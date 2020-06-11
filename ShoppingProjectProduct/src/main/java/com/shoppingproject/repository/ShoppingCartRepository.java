package com.shoppingproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingproject.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long>{

}
