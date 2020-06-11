package com.shoppingproject;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shoppingproject.service.UserService;
import com.shoppingproject.utility.SecurityUtility;
import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.security.Role;
import com.shoppingproject.domain.security.UserRole;

@SpringBootApplication
public class ShoppingProjectApplication implements CommandLineRunner {

	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingProjectApplication.class, args);
	}

	public ShoppingProjectApplication(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run(String... strings) throws Exception {
		Customer user1 = new Customer();
		user1.setFirstName("Elif");
		user1.setLastName("Seven");
		user1.setUsername("e");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p")); ////just testing
		user1.setEmail("elif@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1, userRoles);
	}

}
