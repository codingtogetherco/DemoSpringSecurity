package com.co.coding.toguether.demo.spring.security.config;

import com.co.coding.toguether.demo.spring.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class BasicAuthConfiguration {

//    @Value("${app.security.username}")
//    private String userName;
//
//    @Value("${app.security.password}")
//    private String password;
//
//    @Value("${app.security.rol}")
//    private String rol;
//
//    @Value("${app.security.username2}")
//    private String userNameManager;
//
//    @Value("${app.security.password2}")
//    private String passwordManager;
//
//    @Value("${app.security.rol2}")
//    private String rolManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Deshabilitar CSRF en la nueva versión
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/public").permitAll()
                        .requestMatchers("/auth/login").permitAll()// Public
                        .requestMatchers("/api/private").hasRole("ADMIN")   // Basic Auth
                        .requestMatchers("/api/manager").hasRole("MANAGER")
                        .requestMatchers("/api/admin").authenticated()  // JWT Protected
                )
                .httpBasic(Customizer.withDefaults())  // Configurar Basic Auth
                .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .decoder(jwtDecoder())
                )
        );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName())).build();
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        PasswordEncoder encoder = passwordEncoder();
//        UserDetails userDetails = User.builder().username(userName).password(encoder.encode(password)).roles(rol).build();
//        UserDetails userDetailsManager = User.builder().username(userNameManager).password(encoder.encode(passwordManager)).roles(rolManager).build();
//        return new InMemoryUserDetailsManager(userDetails, userDetailsManager);
//
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
