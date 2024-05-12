package com.madcoder.ch11ex1s2.config;

import com.madcoder.ch11ex1s2.authentication.filter.InitialAuthenticationFilter;
import com.madcoder.ch11ex1s2.authentication.filter.JwtAuthenticationFilter;
import com.madcoder.ch11ex1s2.authentication.provider.OtpAuthenticationProvider;
import com.madcoder.ch11ex1s2.authentication.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public InitialAuthenticationFilter initialAuthenticationFilter() {
        return new InitialAuthenticationFilter();
    }

    @Bean JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(
                initialAuthenticationFilter(),
                BasicAuthenticationFilter.class)
            .addFilterAfter(
                jwtAuthenticationFilter(),
                BasicAuthenticationFilter.class
            );

        http.authorizeRequests().anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobalAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(usernamePasswordAuthenticationProvider);
    }
}