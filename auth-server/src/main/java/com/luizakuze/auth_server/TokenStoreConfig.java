// package com.luizakuze.auth_server;

// import java.security.KeyPair;
// import java.security.KeyPairGenerator;
// import java.security.interfaces.RSAPrivateKey;
// import java.security.interfaces.RSAPublicKey;
// import java.util.UUID;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

// import com.nimbusds.jose.jwk.JWKSet;
// import com.nimbusds.jose.jwk.RSAKey;
// import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
// import com.nimbusds.jose.jwk.source.JWKSource;
// import com.nimbusds.jose.proc.SecurityContext;

// /** 
//  * Configuração para o armazenamento e gerenciamento de tokens.
//  * <p>
//  * Esta classe define a geração e validação de tokens de acesso usando JWK (JSON Web Key) e JWT (JSON Web Token).
//  * </p>
//  */
// @Configuration
// public class TokenStoreConfig {

//     /**
//      * Define um JWKSource que encapsula um par de chaves RSA (pública e privada) para obter access token.
//      * Da forma que está sendo utilizado o bean, toda vez que rodar o código, vai ser gerado um novo par (a partir da lógica em "generateRsaKey")
//      * <p>
//      * Cada vez que o bean é instanciado, um novo par de chaves é gerado para garantir a segurança. 
//      * Essas chaves são usadas para assinar e verificar tokens de acesso.
//      * </p>
//      *
//      * @return um JWKSource imutável contendo o par de chaves RSA gerado
//      */
//     @Bean
//     JWKSource<SecurityContext> jwkSource() {
//         KeyPair keyPair = generateRsaKey();
//         RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//         RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//         RSAKey rsaKey = new RSAKey.Builder(publicKey)
//             .privateKey(privateKey)
//             .keyID(UUID.randomUUID().toString())
//             .build();
//         JWKSet jwkSet = new JWKSet(rsaKey);
//         return new ImmutableJWKSet<>(jwkSet);
//         // colocando um certificado como dependência do projeto, teremos chaves imutáveis!!
//     }
   




//     /**
//      * Configura o `JwtDecoder` para decodificar e validar tokens JWT.
//      * <p>
//      * O `JwtDecoder` verifica a validade dos tokens de acesso com base na chave pública fornecida pelo `JWKSource`.
//      * </p>
//      *
//      * @param jwkSource o `JWKSource` que contém a chave pública usada para validação de tokens
//      * @return uma instância de `JwtDecoder` configurada para decodificação de tokens JWT
//      */
//     @Bean
//     JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//         return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//     }

//     /**
//      * Gera um par de chaves RSA (pública e privada) para assinar tokens.
//      * <p>
//      * Este método cria um par de chaves RSA com comprimento de 2048 bits, utilizado para a geração e validação de tokens de acesso.
//      * </p>
//      *
//      * @return um `KeyPair` RSA contendo as chaves pública e privada
//      * @throws IllegalStateException se ocorrer um erro na geração do par de chaves
//      */
//     private static KeyPair generateRsaKey() {
//         KeyPair keyPair;
//         try {
//             KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//             keyPairGenerator.initialize(2048);
//             keyPair = keyPairGenerator.generateKeyPair();
//         } catch (Exception ex) {
//             throw new IllegalStateException(ex);
//         }
//         return keyPair;
//     }
// }
