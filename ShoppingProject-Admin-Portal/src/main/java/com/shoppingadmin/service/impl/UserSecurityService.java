package com.shoppingadmin.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shoppingadmin.domain.Customer;
import com.shoppingadmin.repository.UserRepository;


@Service
public class UserSecurityService implements UserDetailsService {


    private UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }
}
