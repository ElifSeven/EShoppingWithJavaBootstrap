package com.shoppingproject.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingproject.domain.CartItem;
import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.Product;
import com.shoppingproject.domain.ProductToCartItem;
import com.shoppingproject.domain.ShoppingCart;
import com.shoppingproject.repository.CartItemRepository;
import com.shoppingproject.repository.ProductToCartItemRepository;
import com.shoppingproject.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductToCartItemRepository productToCartItemRepository;
	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
		
		return cartItemRepository.findByShoppingCart(shoppingCart);
		
	}
	
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getProduct().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal  = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		return cartItem;
	}
	
	public 	CartItem addBookToCartItem(Optional<Product> product,Customer user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		for(CartItem cartItem : cartItemList) {
			
			if(product.get().getId() == cartItem.getProduct().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(product.get().getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProduct(product.get());
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(product.get().getOurPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);
		
		ProductToCartItem productToCartItem = new ProductToCartItem();
		productToCartItem.setProduct(product.get());
		productToCartItem.setCartItem(cartItem);
		productToCartItemRepository.save(productToCartItem);
		
		return cartItem;
	
		
	
	}



}
