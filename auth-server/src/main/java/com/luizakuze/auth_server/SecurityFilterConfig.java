
package com.luizakuze.auth_server;

import static org.springframework.security.config.Customizer.withDefaults; // para evitar poluir o código
                                                                           // o que era assim: Costumizer.withDefaults(),
                                                                           // fica assim: withDefaults();
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Security Filters: <p>
 * Endpoints do OpenID e segurança (autenticação)  <p>
 * Oidc padrão, redirect para login page,
 * aceita tokens para userinfo e client registration,
 * requisições autenticadas e formulário de login
 */
@Configuration 
public class SecurityFilterConfig {



    @Bean 
    @Order(1) // qual filtro será executado primeiro? 
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults());

        http.exceptionHandling((exceptions) -> exceptions
        .authenticationEntryPoint(
            new LoginUrlAuthenticationEntryPoint("/login")))
        .oauth2ResourceServer(conf -> conf.jwt(withDefaults()));

        return http.build();
        // http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc( 
        //     withDefaults()) // habilitou oidc
        //     .and() // sempre redirecionada para página de login se não estiver autenticada
        //     .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(
        //         new LoginUrlAuthenticationEntryPoint("/login")))
        //     .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt); // clientes oauth vão ter que ser reconhecidos aqui, são eles que vão reconhecer as tokens de acesso (jwt)            
        //                                                                 openid provê outros endpoints que precisam estar ativos (ex. /userinfo)
        // return http.build();
    }

    @Bean 
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // todas as requisições feitas para o authentication service precisam ser autenticadas
        http.authorizeHttpRequests((authorize) -> authorize
            .anyRequest().authenticated())
        .formLogin(withDefaults()); // usuário estando autenticado, o que a página deve exibir?

        return http.build();
    }
}
