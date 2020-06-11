package com.shoppingproject.service;

import java.util.List;
import java.util.Optional;

import com.shoppingproject.domain.CartItem;
import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.Product;
import com.shoppingproject.domain.ShoppingCart;

public interface CartItemService {
	
	
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	CartItem updateCartItem(CartItem cartItem);

	CartItem addBookToCartItem(Optional<Product> product,Customer user, int qty);
}
