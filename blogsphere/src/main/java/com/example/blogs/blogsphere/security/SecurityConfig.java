package com.example.blogs.blogsphere.security;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers("/api/users/**").hasAuthority("admin")
    //             .requestMatchers("/api/posts/**", "/api/comments/**", "/api/tags/**")
    //             .hasAnyAuthority("admin", "author", "reader")
    //         )
    //         .httpBasic(withDefaults())  // Enable basic HTTP authentication.
    //         .csrf(csrf -> csrf.disable()); // Disable CSRF for stateless REST APIs.
        
    //     return http.build();
    // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


    //     http.authorizeHttpRequests(configurer ->
    //             configurer
                        // // For users
                        // .requestMatchers(HttpMethod.GET, "api/users").hasRole("admin")
                        // .requestMatchers(HttpMethod.GET, "api/users/**").hasAnyRole("admin")
                        // .requestMatchers(HttpMethod.POST, "api/users/**").hasRole("admin") // includes role assignment also
                        // .requestMatchers(HttpMethod.PUT, "api/users/**").hasRole("admin") // includes role deletion also

                        // // For Posts
                        // .requestMatchers(HttpMethod.GET, "api/posts/**").hasAnyRole("admin","author","reader") // includes post retrieve by tag also
                        // .requestMatchers(HttpMethod.GET, "api/posts").hasAnyRole("admin","author","reader")
                        // .requestMatchers(HttpMethod.POST, "api/posts").hasAnyRole("admin","author")
                        // .requestMatchers(HttpMethod.PUT, "api/posts/**").hasAnyRole("admin","author")
                        // .requestMatchers(HttpMethod.DELETE, "api/posts/**").hasAnyRole("admin","author")

                        // // For Comments
                        // .requestMatchers(HttpMethod.GET, "api/comments").hasAnyRole("admin", "author","reader")
                        // .requestMatchers(HttpMethod.GET, "api/comments/**").hasAnyRole("admin", "author","reader")
                        // .requestMatchers(HttpMethod.POST, "api/comments").hasAnyRole("admin","author","reader")
                        // .requestMatchers(HttpMethod.PUT, "api/comments/**").hasAnyRole("admin","author","reader")
                        // .requestMatchers(HttpMethod.DELETE, "api/comments/**").hasAnyRole("admin","author","reader")


                        // // For Tags
                        // .requestMatchers(HttpMethod.GET, "api/tags").hasAnyRole("admin","author","reader")
                        // .requestMatchers(HttpMethod.GET, "api/tags/**").hasAnyRole("admin","author","reader")
                        // .requestMatchers(HttpMethod.POST, "api/tags").hasAnyRole("admin","author")
                        // .requestMatchers(HttpMethod.PUT, "api/tags/**").hasAnyRole("admin","author")
                        // .requestMatchers(HttpMethod.DELETE, "api/tags/**").hasAnyRole("admin","author")

                        // // Extra feature
                        // .requestMatchers(HttpMethod.POST, "api/posts/**").hasAnyRole("admin","author") // => to assign tags to posts later
                        // .requestMatchers(HttpMethod.DELETE, "api/posts/**").hasAnyRole("admin","author") // => to remove tags from posts later

    //     );


    //     // use HTTP Basic authentication


    //     http.httpBasic(Customizer.withDefaults());


    //     // disable Cross Site Request Forgery (CSRF)
    //     // in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
    //     http.csrf(AbstractHttpConfigurer::disable);


    //     return http.build();
    // }



    // Configure the security filter chain using the lambda DSL.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                                        // For users
                                        .requestMatchers(HttpMethod.GET, "api/users").hasAuthority("admin")
                                        .requestMatchers(HttpMethod.GET, "api/users/**").hasAnyAuthority("admin")
                                        .requestMatchers(HttpMethod.POST, "api/users/**").hasAuthority("admin") // includes role assignment also
                                        .requestMatchers(HttpMethod.PUT, "api/users/**").hasAuthority("admin") // includes role deletion also
                
                                        // For Posts
                                        .requestMatchers(HttpMethod.GET, "api/posts/**").hasAnyAuthority("admin","author","reader") // includes post retrieve by tag also
                                        .requestMatchers(HttpMethod.GET, "api/posts").hasAnyAuthority("admin","author","reader")
                                        .requestMatchers(HttpMethod.POST, "api/posts").hasAnyAuthority("admin","author")
                                        .requestMatchers(HttpMethod.PUT, "api/posts/**").hasAnyAuthority("admin","author")
                                        .requestMatchers(HttpMethod.DELETE, "api/posts/**").hasAnyAuthority("admin","author")
                
                                        // For Comments
                                        .requestMatchers(HttpMethod.GET, "api/comments").hasAnyAuthority("admin", "author","reader")
                                        .requestMatchers(HttpMethod.GET, "api/comments/**").hasAnyAuthority("admin", "author","reader")
                                        .requestMatchers(HttpMethod.POST, "api/comments").hasAnyAuthority("admin","author","reader")
                                        .requestMatchers(HttpMethod.PUT, "api/comments/**").hasAnyAuthority("admin","author","reader")
                                        .requestMatchers(HttpMethod.DELETE, "api/comments/**").hasAnyAuthority("admin","author","reader")
                
                
                                        // For Tags
                                        .requestMatchers(HttpMethod.GET, "api/tags").hasAnyAuthority("admin","author","reader")
                                        .requestMatchers(HttpMethod.GET, "api/tags/**").hasAnyAuthority("admin","author","reader")
                                        .requestMatchers(HttpMethod.POST, "api/tags").hasAnyAuthority("admin","author")
                                        .requestMatchers(HttpMethod.PUT, "api/tags/**").hasAnyAuthority("admin","author")
                                        .requestMatchers(HttpMethod.DELETE, "api/tags/**").hasAnyAuthority("admin","author")
                
                                        // Extra feature
                                        .requestMatchers(HttpMethod.POST, "api/posts/**").hasAnyAuthority("admin","author") // => to assign tags to posts later
                                        .requestMatchers(HttpMethod.DELETE, "api/posts/**").hasAnyAuthority("admin","author") // => to remove tags from posts later                
            )
            .httpBasic(withDefaults())  // Enable basic HTTP authentication.
            .csrf(csrf -> csrf.disable()); // Disable CSRF for stateless REST APIs.
        
        return http.build();
    }
}
