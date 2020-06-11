package com.shoppingadmin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shoppingadmin.domain.Customer;
import com.shoppingadmin.domain.security.Role;
import com.shoppingadmin.domain.security.UserRole;
import com.shoppingadmin.utility.SecurityUtility;
import com.shoppingadmin.service.impl.UserService;

@SpringBootApplication
public class ShoppingProjectAdminPortalApplication implements CommandLineRunner {
	
	private UserService userService;

	public ShoppingProjectAdminPortalApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ShoppingProjectAdminPortalApplication.class, args);
	}
	
	@Override
	public void run(String... strings) throws Exception {

		Customer user1 = new Customer();
		user1.setUsername("admin");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		user1.setEmail("admin@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(0);
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1,role1));

		userService.createUser(user1,userRoles);

	}

}
