package com.madcoder.ch14ex1as.config;

import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
        throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    // Configuration 코드 단에서 설정할 때, 사용
    // application.yml 에서는 생성할 Token 타입(JWT / Opaque Token) 관련 property를 설정할 수가 없어 여기서 설정함.
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
                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                    .build()
            )
            .build();

        return new InMemoryRegisteredClientRepository(resourceServer);
    }
}