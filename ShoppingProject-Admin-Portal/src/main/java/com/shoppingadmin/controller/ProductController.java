package com.shoppingadmin.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingadmin.domain.Product;
import com.shoppingadmin.service.impl.ProductService;

@Controller
@RequestMapping("/product") //// root path!!
public class ProductController {

	@Autowired
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "addProduct";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product) {

		productService.save(product);

		MultipartFile productImage = product.getProductImage();

		try {
			byte[] bytes = productImage.getBytes();
			String name = product.getId() + ".jpg";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/images/product/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:productList";

	}

	@RequestMapping("/productList")
	public String productList(Model model) {

		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);

		return "productList";
	}

	@RequestMapping("/productInfo")
	public String productInfo(@RequestParam("id") Long id, Model model) {
		Optional<Product> product = productService.findById(id);
		model.addAttribute("product", product.get());
		return "productInfo";
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.GET)
	public String updateProduct(@RequestParam("id") Long id, Model model) {
		Optional<Product> product = productService.findById(id);
		model.addAttribute("product", product.get());
		return "updateProduct";
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public String updateProductPost(@ModelAttribute("product") Product product, Model model) {

		productService.save(product);
		MultipartFile productImage = product.getProductImage();

		if (!productImage.isEmpty()) {
			String name = product.getId() + ".jpg";
			try {
				byte[] bytes = productImage.getBytes();
				Files.delete(Paths.get("src/main/resources/static/images/product/" + name));
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/images/product/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("product", product);
		return "redirect:productInfo?id=" + product.getId();
	}

}
