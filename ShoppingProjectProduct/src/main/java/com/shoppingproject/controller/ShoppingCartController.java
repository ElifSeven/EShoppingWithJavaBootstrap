package com.shoppingproject.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingproject.domain.CartItem;
import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.Product;
import com.shoppingproject.domain.ShoppingCart;
import com.shoppingproject.service.CartItemService;
import com.shoppingproject.service.ProductService;
import com.shoppingproject.service.ShoppingCartService;
import com.shoppingproject.service.UserService;

@Controller
@RequestMapping("/shoppingCart")

public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		Customer customer = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = customer.getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		shoppingCartService.updateShoppingCart(shoppingCart);

		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);

		return "shoppingCart";
	}

	@RequestMapping("/addItem")
	public String addItem(@ModelAttribute("product") Optional<Product> product, @ModelAttribute("qty") String qty,
			Model model, Principal principal) {

		Customer user = userService.findByUsername(principal.getName());
		product = productService.findById(product.get().getId());

		if (Integer.parseInt(qty) > product.get().getInStockNumber()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/productDetail?id = " + product.get().getId();

		}

		CartItem cartItem = cartItemService.addBookToCartItem(product, user, Integer.parseInt(qty));
		model.addAttribute("addBookSuccess", true);

		return "forward:/productDetail?id=" + product.get().getId();
	}
}
