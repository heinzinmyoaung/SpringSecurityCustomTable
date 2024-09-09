package com.example.springSecurity.security;

import com.example.springSecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserAccSecurity {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Plain text passwords (use only for testing)
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService); //set the custom user details service
        provider.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt

        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.authorizeHttpRequests(configure ->
                configure
                        .requestMatchers(HttpMethod.GET, "/").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/create").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/update/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/register").hasRole("EMPLOYEE")

        );

        //use Http Basic authentication
        httpSecurity.httpBasic(Customizer.withDefaults());

        // disable CSRF,general, not required for stateless RESTapi (use POST, PUT, DELETE, and PATCH)
        httpSecurity.csrf(csrf ->csrf.disable());

        return httpSecurity.build();
    }
}



//    @Bean
//    UserDetailsManager userDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        // define query to retrieve a user by email
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "select user_email, password, status from users WHERE user_email=?"
//        );
//
//        // define query to retrieve the authorities/roles by email
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "select role_name, user_email from roles WHERE user_email=?"
//        );
//
//        return jdbcUserDetailsManager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();  // Plain text passwords (use only for testing)
//    }