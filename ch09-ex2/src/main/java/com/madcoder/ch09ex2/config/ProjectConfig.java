package com.madcoder.ch09ex2.config;

import com.madcoder.ch09ex2.filter.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Autowired
    private StaticKeyAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAt(filter,
                BasicAuthenticationFilter.class)
            .authorizeRequests().anyRequest().permitAll();

        return http.build();
    }
}
