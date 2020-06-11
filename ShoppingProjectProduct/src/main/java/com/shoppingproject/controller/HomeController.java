package com.shoppingproject.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.Product;
import com.shoppingproject.domain.security.PasswordResetToken;
import com.shoppingproject.domain.security.Role;
import com.shoppingproject.domain.security.UserRole;
import com.shoppingproject.service.ProductService;
import com.shoppingproject.service.UserService;
import com.shoppingproject.service.impl.UserSecurityService;
import com.shoppingproject.utility.MailConstructor;
import com.shoppingproject.utility.SecurityUtility;

@Controller
public class HomeController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;

	@Autowired
	private ProductService productService;

	
	public HomeController(UserService userService, UserSecurityService userSecurityService, JavaMailSender mailSender,
			MailConstructor mailConstructor) {
		this.userService = userService;
		this.userSecurityService = userSecurityService;
		this.mailSender = mailSender;
		this.mailConstructor = mailConstructor;
	}

	@RequestMapping({ "", "/", "index", "index.html" })
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, Model model) throws Exception {
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);

			return "myAccount";
		}
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);

			return "myAccount";
		}
		Customer user = new Customer();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenforUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(email);
		model.addAttribute("emailSent", true);

		return "myAccount";
	}

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);

		if (passwordResetToken == null) {
			String message = "Invalid Token";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		Customer user = passwordResetToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("classActiveEdit", true);
		model.addAttribute("user", user);
		return "myProfile";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}

	@RequestMapping("/productshelf")
	public String productshelf(Model model) {

		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		return "productshelf";

	}

	@RequestMapping("/productDetail")
	public String productDetail(@PathParam("id") Long id, Model model, Principal principal) {

		if (principal != null) {
			String username = principal.getName();
			Customer user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		Optional<Product> product = productService.findById(id);
		model.addAttribute("product", product.get());

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 18);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);

		return "productDetail";

	}

	@RequestMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

		model.addAttribute("classActiveForgetPassword", true);

		Customer user = userService.findByEmail(email);

		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		} else {
			String password = SecurityUtility.randomPassword();

			String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
			user.setPassword(encryptedPassword);

			userService.save(user);

			String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenforUser(user, token);

			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token,
					user, password);

			mailSender.send(newEmail);
			model.addAttribute("forgetPasswordEmailSent", true);
		}

		return "myAccount";
	}

}
