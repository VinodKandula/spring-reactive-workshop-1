package com.example.productclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.productclient.LogUtil.logTime;

public class Client3 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        List<Mono<Product>> products = Stream.of(1, 2, 3)
                .map(i -> client.get().uri("/products/{id}", i).retrieve().bodyToMono(Product.class))
                .collect(Collectors.toList());

        // with `when()` we aggregate given publishers into a new Mono that will fulfilled when all of the given Publishers have completed.
        Mono.when(products);

        // why it still returns around 100 ms ?
        logTime(start);
    }

}
