package com.co.coding.toguether.demo.spring.security.service;

import com.co.coding.toguether.demo.spring.security.entity.RoleEntity;
import com.co.coding.toguether.demo.spring.security.entity.UserEntity;
import com.co.coding.toguether.demo.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String authorities = user.getRoleEntities().stream()
            .map(RoleEntity::getName)
            .collect(Collectors.joining(", "));

    return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(authorities)
            .build();
    }

}


