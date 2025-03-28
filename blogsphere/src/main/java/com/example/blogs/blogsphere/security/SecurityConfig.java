package com.example.blogs.blogsphere.security;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Define a PasswordEncoder using BCrypt.
    // This DelegatingPasswordEncoder is configured to use "bcrypt" as the default encoding scheme.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder("bcrypt", 
                Map.of("bcrypt", new BCryptPasswordEncoder()));
    }

    // Configure a JdbcUserDetailsManager to load user details from your DataSource.
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        
        // Query to load user details from the "users" table.
        manager.setUsersByUsernameQuery(
            "select user_name, password, true as enabled from users where user_name=?"
        );
        
        // Query to load authorities (roles) from a join between "users" and "roles".
        manager.setAuthoritiesByUsernameQuery(
            "select u.user_name, r.role_name as authority from users u join roles r on u.user_id = r.user_id where u.user_name=?"
        );
        
        // Use the role names exactly as stored (e.g., "admin", "author", "reader").
        manager.setRolePrefix("");
        return manager;
    }
    
    // Configure the security filter chain using the lambda DSL.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/users/**").hasAuthority("admin")
                .requestMatchers("/api/posts/**", "/api/comments/**", "/api/tags/**")
                .hasAnyAuthority("admin", "author", "reader")
            )
            .httpBasic(withDefaults())  // Enable basic HTTP authentication.
            .csrf(csrf -> csrf.disable()); // Disable CSRF for stateless REST APIs.
        
        return http.build();
    }
}
