package com.madcoder.ch08ex4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
            .requestMatchers( "/product/{code:^[0-9]*$}").permitAll()
            .anyRequest().denyAll();

        return http.build();
    }
}
