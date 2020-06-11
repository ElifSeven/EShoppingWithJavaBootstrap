package com.shoppingproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingproject.domain.ProductToCartItem;

public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long> {

}
