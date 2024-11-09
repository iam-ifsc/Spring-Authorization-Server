package com.luizakuze.auth_server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

/** 
 * Token Store: <p>
 * Codificação (gerar token) e decodificação dos tokens de acesso <p>
 * JWKSource (gerar) e JwtDecoder (decodificar)
 */
@Configuration
public class TokenStoreConfig {
    // Para configurar a token store, é preciso criar um par de chaves


    @Bean
    JWKSource<SecurityContext> jwkSource() {
        // jwkSource encapsula um par de chaves: pública e privada, criadas para acessar o token de acesso
        // da forma que está sendo utilizado o bean, toda vez que rodar o código, vai ser gerado um novo par (a partir da lógica em "generateRsaKey")
        // colocando um certificado como dependência do projeto, teremos chaves imutáveis (geralmente é feito isso)
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        // para verificar se o token é válido
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
        throw new IllegalStateException(ex);
        }
        return keyPair;
  }
  
    
}
