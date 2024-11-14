
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


    /**
     * Configura a cadeia de filtros de segurança para o servidor de autorização.
     * <p>
     * Define as configurações de segurança padrão do servidor de autorização,
     * ativa o OpenID Connect (OIDC) e define o ponto de entrada de autenticação.
     * Este filtro será executado com prioridade.
     * </p>
     *
     * @param http o objeto HttpSecurity usado para configurar a segurança
     * @return a cadeia de filtros de segurança configurada
     * @throws Exception em caso de erro na configuração do HttpSecurity
     */
    @Bean 
    @Order(1) // qual filtro será executado primeiro? 
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // "Servidor de autorização deve obedecer o protocolo OAuth2"
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

    /**
     * Configura a cadeia de filtros de segurança padrão para o servidor.
     * <p>
     * Garante que todas as requisições feitas ao serviço de autenticação sejam autenticadas
     * e exibe um formulário de login para usuários não autenticados.
     * Este filtro será executado após o filtro do servidor de autorização.
     * </p>
     *
     * @param http o objeto HttpSecurity usado para configurar a segurança
     * @return a cadeia de filtros de segurança configurada
     * @throws Exception em caso de erro na configuração do HttpSecurity
     */
    @Bean 
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // todas as requisições feitas para o authentication service precisam ser autenticadas!
        http.authorizeHttpRequests((authorize) -> authorize 
            .anyRequest().authenticated())
        .formLogin(withDefaults()); // usuário estando autenticado, qual a página deve exibir?

        return http.build();
    }
}
