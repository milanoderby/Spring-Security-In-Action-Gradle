package com.madcoder.ch14ex1rs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorization ->
                authorization
                    .requestMatchers(HttpMethod.GET, "/hello").hasAuthority("SCOPE_info")
                    .requestMatchers(HttpMethod.GET, "/user").hasAuthority("SCOPE_read")
                    .anyRequest().authenticated()
            )
            // application.yml 에서 설정할 때, 사용
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken)
        
            // Configuration 코드 단에서 설정할 때, 사용
//            .oauth2ResourceServer(oauth2ResourceServer  -> oauth2ResourceServer.opaqueToken(opaqueTokenConfigurer -> {
//                opaqueTokenConfigurer
//                    .introspectionUri("http://localhost:8082/oauth2/introspect")
//                    .introspectionClientCredentials("resource-server", "resource-server-secret");
//            }))
        ;
        return http.build();
    }
}