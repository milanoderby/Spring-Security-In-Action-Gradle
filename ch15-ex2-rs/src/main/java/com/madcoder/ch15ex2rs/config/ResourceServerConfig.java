package com.madcoder.ch15ex2rs.config;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
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

    @Value("${jwt.rsa-key.public-key}")
    private String RSAPublicKey;

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
    public JwtDecoder jwtDecoder() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
    }

    // property 파일에 있는 RSA 공개키를 가져옴
    private RSAPublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // RSA 공개키 로드
        byte[] RSAPublicKeyBytes = Base64.getDecoder().decode(RSAPublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(RSAPublicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        return (RSAPublicKey) publicKey;
    }
}