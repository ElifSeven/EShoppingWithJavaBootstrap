package com.shoppingproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shoppingproject.domain.CartItem;
import com.shoppingproject.domain.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
