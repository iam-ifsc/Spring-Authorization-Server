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

/**
 * Configuração para o armazenamento de clientes OAuth2.
 * <p>
 * Esta classe define um cliente OAuth2 registrado, que será utilizado para gerenciar
 * o acesso a recursos protegidos por meio de um servidor de autorização.
 * Ela limita o acesso a aplicações confiáveis, configurando um cliente com 
 * métodos de autenticação e escopos específicos.
 * </p>
 */
@Configuration
public class ClientStoreConfig {

    /**
     * Configura e registra um cliente OAuth2 no repositório de clientes.
     * <p>
     * O cliente registrado possui um ID único, clientId, clientSecret e métodos
     * de autenticação e autorização específicos. Ele também define o URI de redirecionamento,
     * escopos de acesso e requer consentimento explícito do usuário para acessar dados
     * protegidos.
     * </p>
     * 
     * @return um repositório de clientes em memória contendo o cliente registrado
     */
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
