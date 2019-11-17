package nl.altindag.client.service;

import static nl.altindag.client.Constants.HEADER_KEY_CLIENT_TYPE;

import org.springframework.web.reactive.function.client.WebClient;

import nl.altindag.client.ClientType;
import nl.altindag.client.model.ClientResponse;

public abstract class SpringWebClientWrapper extends RequestService {

    private final WebClient webClient;

    public SpringWebClientWrapper(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ClientResponse executeRequest(String url) throws Exception {
        return webClient.get()
                        .uri(url)
                        .header(HEADER_KEY_CLIENT_TYPE, getClientType().getValue())
                        .exchange()
                        .flatMap(clientResponse -> clientResponse.toEntity(String.class))
                        .map(responseEntity -> new ClientResponse(responseEntity.getBody(), responseEntity.getStatusCodeValue()))
                        .block();
    }

    protected abstract ClientType getClientType();

}
