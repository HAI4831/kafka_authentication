//// SecurityConfig.java
//package com.run.serviceb;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf().disable()
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.POST, "/api/messages/**").authenticated()
//                .anyExchange().permitAll()
//                .and()
//                .oauth2ResourceServer()
//                .jwt()
//                .and()
//                .and()
//                .build();
//    }
//}
