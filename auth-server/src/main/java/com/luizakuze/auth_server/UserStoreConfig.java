package com.luizakuze.auth_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * User Store: <p>
 * Usuários registrados no AS <p>
 * {username, password, roles}
 */
@Configuration
public class UserStoreConfig {
    
    @Bean
    UserDetailsService userDetailsService() {
        // consultar usuários no processo de autenticação
        var userDetailsManager = new InMemoryUserDetailsManager(); // em memória (poderia ser em um banco)

        userDetailsManager.createUser(
            User.withUsername("user")
                .password("{noop}password") // noop salva em texto puro (mundo real não é assim. é criptografado! :D)
                .roles("USER") // papéis que o usuário possui
                .build());

            return userDetailsManager;


    }
}
