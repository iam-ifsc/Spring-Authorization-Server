package com.luizakuze.auth_server;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;


// Oauth é sobre aturoizar um terceiro a acessar um recurso protegido
// Limitar acesso apenas às aplicações confiáveis

@Configuration
public class ClientStoreConfig {
    
    @Bean
    RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString()) // registroq ue está salvo
            .clientId("client-server")// quem é o cliente, por qual nome devo chamar ele?
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // método de autenticação
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // qual será o fluxo de autenticação 
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-server-oidc")// qual o uri da aplicação cliente que deve receber o code gerado que é necessário para pedir o access token (authorization code)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(true).build()) // informando para o usuário aceitar termo de consentimento dos dados
            .build();
        
        return new InMemoryRegisteredClientRepository(registeredClient);// tem que retornar um repositório
        }
}
