package com.example.graphql.externalservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.GraphQlRequest;
import org.springframework.graphql.client.WebGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class InternalService {

    WebClient webClient;

    WebGraphQlClient webGraphQlClient;

    public InternalService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/graphql") // Ensure this is the correct endpoint
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public String getInternalData() {

        String query = "query authorDetails {\n" +
                "  authorById(id: \"author-2\") {\n" +
                "    id\n" +
                "    firstName\n" +
                "    lastName\n" +
                "  }\n" +
                "}";

        String response = this.webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData("query", query))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;

    }

    public String getInternalData2() {
        this.webGraphQlClient = WebGraphQlClient.builder(WebClient.builder()
                        .baseUrl("http://localhost:8080/graphql")
                        .build())
          x        .build();

        String query = "query authorDetails {\n" +
                "  authorById(id: \"author-2\") {\n" +
                "    id\n" +
                "    firstName\n" +
                "    lastName\n" +
                "  }\n" +
                "}";

        Mono<Map> resp = webGraphQlClient.document(query)
                .retrieve("data")
                .toEntity(Map.class);

        log.info(" resp {}", resp);

        return null;

    }

}
