package com.luizakuze.auth_server;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

/** AS Settings <p>
 * Configurações padrão + Costumizações <p>
 * AuthorizationServerSettings
 */
@Configuration
public class AuthorizationServerConfig {
    
    @Bean
    AuthorizationServerSettings authorizationServerSettings() {

        return AuthorizationServerSettings.builder().build();
        
        // .jwkSetEndpoint() método para colocar uma configuração com uma uri que tem a chave lá (ao invés de colocar chave em memória)
    }
}
