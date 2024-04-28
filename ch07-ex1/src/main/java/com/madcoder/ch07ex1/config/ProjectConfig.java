package com.madcoder.ch07ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("john")
            .password("12345")
            .authorities("READ")
            .build();

        UserDetails user2 = User.withUsername("jane")
            .password("12345")
            .authorities("WRITE")
            .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        //http.authorizeRequests().anyRequest().hasAnyAuthority("WRITE", "READ");
        //http.authorizeRequests().anyRequest().hasAuthority("WRITE");
        http.authorizeRequests().anyRequest().access("hasAuthority('WRITE')");
        return http.build();
    }
}
