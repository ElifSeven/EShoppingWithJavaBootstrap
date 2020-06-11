package com.shoppingadmin.service.impl;


import com.shoppingadmin.domain.Customer;
import com.shoppingadmin.domain.security.UserRole;

import java.util.Set;

public interface UserService {


    Customer createUser(Customer user, Set<UserRole> userRoles) throws Exception;

    Customer save(Customer user);
}
