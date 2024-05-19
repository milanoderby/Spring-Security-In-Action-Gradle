package com.madcoder.ch15ex1rs.config;

import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

    @Value("${jwt.key}")
    private String jwtKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorization ->
                authorization
                    .requestMatchers(HttpMethod.GET, "/hello").hasAuthority("SCOPE_info")
                    .requestMatchers(HttpMethod.GET, "/user").hasAuthority("SCOPE_read")
                    .anyRequest().authenticated()
            )
            // application.yml 에서 프로퍼티가 지정되었을 때, 프로퍼티들이 사용될 수 있게하는 설정
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        ;
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(getSecretKey()).build();
    }

    // 사용자 지정 문자열을 SecretKey로 변환
    private SecretKeySpec getSecretKey() {
        byte[] secretBytes = jwtKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretBytes, "HmacSha256");
        return secretKeySpec;
    }
}