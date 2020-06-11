package com.shoppingadmin.service.impl;

import com.shoppingadmin.domain.Customer;
import com.shoppingadmin.domain.security.UserRole;
import com.shoppingadmin.repository.RoleRepository;
import com.shoppingadmin.repository.UserRepository;
import com.shoppingadmin.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public Customer createUser(Customer user, Set<UserRole> userRoles) {
		Customer localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("user {} already exists.Nothing will be done.", user.getUsername());

		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());

			}
			user.getUserRoles().addAll(userRoles);
			localUser = userRepository.save(user);
		}
		return localUser;
	}

	@Override
	public Customer save(Customer user) {
		return userRepository.save(user);
	}
}
