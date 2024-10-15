//// SecurityConfig.java
//package com.run.servicea;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//    @Value("${key.location}")
//    RSAPublicKey key;
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers(HttpMethod.POST, "/api/messages/**").authenticated()
//                        .anyExchange().permitAll()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .and()
//                .build();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(RSAPublicKey key) {
//        return NimbusJwtDecoder.withPublicKey(key).build();
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
//
//@Bean
//BeanFactoryPostProcessor conversionServiceCustomizer() {
//    return beanFactory ->
//            beanFactory.getBean(RsaKeyConversionServicePostProcessor.class)
//                    .setResourceLoader(new CustomResourceLoader());
//}
//
//@Bean
//
//
//
//import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;
//
//@Configuration
//@EnableWebSecurity
//public class DirectlyConfiguredJwkSetUri {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/contacts/**").access(hasScope("contacts"))
//                        .requestMatchers("/messages/**").access(hasScope("messages"))
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//        return http.build();
//    }
//}
//
//@PreAuthorize("hasAuthority('SCOPE_messages')")
//public List<Message> getMessages(...) {}
//
//@Bean
//public JwtAuthenticationConverter jwtAuthenticationConverter() {
//    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//    grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//
//    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//    return jwtAuthenticationConverter;
//}