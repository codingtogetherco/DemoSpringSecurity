package com.co.coding.together.demo.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthConfiguration {

    @Value("${app.security.username}")
    private String userName;

    @Value("${app.security.password}")
    private String password;

    @Value("${app.security.rol}")
    private String rol;

    @Value("${app.security.username2}")
    private String userNameManager;

    @Value("${app.security.password2}")
    private String passwordManager;

    @Value("${app.security.rol2}")
    private String rolManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest.requestMatchers("/api/public").permitAll()
                                .requestMatchers("/api/private").hasRole(rol)
                                .requestMatchers("/api/manager").hasRole(rolManager)
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        PasswordEncoder encoder = passwordEncoder();
        UserDetails userDetails = User.builder().username(userName).password(encoder.encode(password)).roles(rol).build();
        UserDetails userDetailsManager = User.builder().username(userNameManager).password(encoder.encode(passwordManager)).roles(rolManager).build();
        return new InMemoryUserDetailsManager(userDetails, userDetailsManager);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}
