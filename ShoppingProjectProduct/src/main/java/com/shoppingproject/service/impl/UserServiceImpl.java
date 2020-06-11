package com.shoppingproject.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.ShoppingCart;
import com.shoppingproject.domain.security.PasswordResetToken;
import com.shoppingproject.domain.security.UserRole;
import com.shoppingproject.repository.PasswordResetTokenRepository;
import com.shoppingproject.repository.RoleRepository;
import com.shoppingproject.repository.UserRepository;
import com.shoppingproject.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	private PasswordResetTokenRepository passwordResetTokenRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;

	public UserServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository,
			RoleRepository roleRepository) {
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenforUser(final Customer user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public Customer findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Customer findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	//@Transactional

	public Customer createUser(Customer user, Set<UserRole> userRoles) throws Exception {
		Customer localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("user {} already exists.Nothing will be done.", user.getUsername());

		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());

			}
			user.getUserRoles().addAll(userRoles);

			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setCustomer(user);
			user.setShoppingCart(shoppingCart);

			localUser = userRepository.save(user);
		}
		return localUser;
	}

	@Override
	public Customer save(Customer user) {
		return userRepository.save(user);
	}
}
