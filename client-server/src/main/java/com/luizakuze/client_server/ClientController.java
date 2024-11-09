package com.luizakuze.client_server;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {

    // WebClient usado para acessar o resource server
    private final WebClient webClient;

    // Configuração do WebClient para acessar o resource server na porta 9090
    public ClientController(WebClient.Builder builder) {
        this.webClient = builder
            .baseUrl("http://127.0.0.1:9090") // Poderia ser configurado via application.properties
            .build();
    }

    // Endpoint para obter as tarefas do usuário logado, acessando o resource server
    @GetMapping("/tasks")
    public Mono<String> getTasks(
        @RegisteredOAuth2AuthorizedClient("client-server-oidc") OAuth2AuthorizedClient client) {
        return webClient
            .get()
            .uri("/tasks")
            .header("Authorization", "Bearer %s".formatted(client.getAccessToken().getTokenValue()))
            .retrieve()
            .bodyToMono(String.class);
    }

    // Endpoint para retornar informações sobre o usuário autenticado após o login
    // O retorno é do tipo Mono, usado para representar uma operação assíncrona que retorna um único valor
    @GetMapping("/home")
    public Mono<String> home(
        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client,
        @AuthenticationPrincipal OidcUser oidcUser) {
        return Mono.just("""
            <h2>Access Token: %s</h2>
            <h2>Refresh Token: %s</h2>
            <h2>ID Token: %s</h2>
            <h2>Claims: %s</h2>
        """.formatted(
            client.getAccessToken().getTokenValue(),
            client.getRefreshToken().getTokenValue(),
            oidcUser.getIdToken().getTokenValue(),
            oidcUser.getClaims()));
    }
}

/*
Explicação dos Tokens:
- Access Token: Usado para autorizar requisições ao resource server.
- Refresh Token: Utilizado para obter um novo access token quando o atual expira.
- ID Token: Contém informações sobre o usuário autenticado.
- Claims: Propriedades associadas ao usuário (nome, endereço, etc.) baseadas no token recebido.
*/
