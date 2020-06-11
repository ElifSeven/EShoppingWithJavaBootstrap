package com.shoppingproject.service;

import java.util.Set;

import com.shoppingproject.domain.Customer;
import com.shoppingproject.domain.security.PasswordResetToken;

public interface UserService {


    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenforUser(final Customer user, final String token);

    Customer findByUsername(String username);

    Customer findByEmail(String email);

    Customer createUser(Customer user, Set<com.shoppingproject.domain.security.UserRole> userRoles) throws Exception;

    Customer save(Customer user);
}