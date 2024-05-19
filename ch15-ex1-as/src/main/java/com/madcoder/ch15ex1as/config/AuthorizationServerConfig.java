package com.madcoder.ch15ex1as.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthorizationServerConfig {

    @Value("${jwt.key}")
    private String jwtKey;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
        throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    // Configuration 코드 단에서 설정할 때, 사용
    // application.yml 에서는 생성할 Token 타입(JWT / Opaque Token) 관련 property를 설정할 수가 없어 여기서 설정함.
    // 이 예제에서는 JWT를 사용하고, 기본 Token 타입이 JWT 이기 때문에 application.yml 에서 설정하는 것도 가능
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient resourceServer = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("resource-server")
            .clientSecret("{noop}resource-server-secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope("info")
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .build()
            )
            .build();

        return new InMemoryRegisteredClientRepository(resourceServer);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        SecretKeySpec secretKeySpec = getSecretKey();

        // SecretKey를 이용하여 JWK 생성
        OctetSequenceKey hmacKey = new OctetSequenceKey.Builder(secretKeySpec)
            .keyID(UUID.randomUUID().toString())
            .algorithm(JWSAlgorithm.HS256)
            .build();

        JWKSet jwkSet = new JWKSet(hmacKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // 사용자 지정 문자열을 SecretKey로 변환
    private SecretKeySpec getSecretKey() {
        byte[] secretBytes = jwtKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretBytes, "HmacSha256");
        return secretKeySpec;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    // 참고: https://stackoverflow.com/questions/70944103/failed-to-select-a-jwk-signing-key-trying-to-implement-elliptic-curve-keys-to
    // 참고: https://github.com/spring-projects/spring-authorization-server/issues/487
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> context.getJwsHeader().algorithm( MacAlgorithm.HS256 );
    }
}