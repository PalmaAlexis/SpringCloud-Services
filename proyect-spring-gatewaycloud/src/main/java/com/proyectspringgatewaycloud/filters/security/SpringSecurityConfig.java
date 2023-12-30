package com.proyectspringgatewaycloud.filters.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Bean
	SecurityWebFilterChain configurer(ServerHttpSecurity http) {
		return http.authorizeExchange( p -> 
		  p.pathMatchers("/api/security/**").permitAll()
		  .pathMatchers(HttpMethod.GET,"/api/product/allProducts",
				  "/api/item/allItems", "/api/user/users").permitAll()
		  .pathMatchers(HttpMethod.GET, "/api/product/id/{id}",
				  "/api/item/id/{id}/quantity/{quantity}").hasAnyRole("USER", "ADMIN")
		  .pathMatchers("/api/product/**", "/api/item/**", "/api/user/**").hasRole("ADMIN")
		  .and().addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
		  .csrf( c -> c.disable())
		  ).build();
	}
	


}
