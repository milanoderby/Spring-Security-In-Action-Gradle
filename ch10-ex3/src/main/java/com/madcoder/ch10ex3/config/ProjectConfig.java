package com.madcoder.ch10ex3.config;

import com.madcoder.ch10ex3.csrf.CustomCsrfTokenRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName(null);

        http.csrf(c -> {
            c.csrfTokenRepository(customTokenRepository());
            c.ignoringRequestMatchers(PathRequest.toH2Console());
            c.ignoringRequestMatchers("/ciao");
//            c.csrfTokenRequestHandler(requestHandler);
            c.csrfTokenRequestHandler(requestHandler);
        });

        http.headers(headers -> headers.frameOptions().disable());

        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(PathRequest.toH2Console()).permitAll()
            .anyRequest().permitAll()
        );

        return http.build();
    }
}