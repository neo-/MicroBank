package com.naveejr.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(p -> p
                        .path("/microbank/accounts/**")
                        .filters(f -> f.rewritePath("/microbank/accounts/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTS")
                )
                .route(p -> p
                        .path("/microbank/cards/**")
                        .filters(f -> f.rewritePath("/microbank/cards/(?<segment>.*)", "/${segment}"))
                        .uri("lb://CARDS")
                )
                .route(p -> p
                        .path("/microbank/loans/**")
                        .filters(f -> f.rewritePath("/microbank/loans/(?<segment>.*)", "/${segment}"))
                        .uri("lb://LOANS")
                )
                .build();
    }
}
