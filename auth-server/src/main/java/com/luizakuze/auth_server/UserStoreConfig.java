package com.luizakuze.auth_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configuração de armazenamento de usuários.
 * <p>
 * Esta classe registra usuários no servidor de autorização (AS) com informações de
 * nome de usuário, senha e funções (roles). Os usuários são armazenados em memória.
 * </p>
 */
@Configuration
public class UserStoreConfig {
    
    /**
     * Define um serviço de detalhes de usuário que armazena usuários em memória.
     * <p>
     * Este método cria um `InMemoryUserDetailsManager`, que armazena detalhes dos usuários
     * para autenticação. Aqui, um usuário de exemplo é criado com nome de usuário "user",
     * senha "password" e função "USER".
     * </p>
     *
     * @return um `UserDetailsService` que contém os detalhes do usuário armazenado em memória
     */
    @Bean
    UserDetailsService userDetailsService() {
        // Consultar usuários no processo de autenticação
        var userDetailsManager = new InMemoryUserDetailsManager(); // Armazenamento em memória (poderia ser em um banco)

        userDetailsManager.createUser(
            User.withUsername("user")
                .password("{noop}password") // "{noop}" indica que a senha está em texto puro (no mundo real, deve ser criptografada)
                .roles("USER") // Funções que o usuário possui
                .build());
        return userDetailsManager;
    }
}