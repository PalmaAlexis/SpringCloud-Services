package com.proyectspringzuul.oauth;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${config.security.jwt.key}")
	private String jwtKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(r -> {
			try {
				r.antMatchers("/api/security/oauth/**").permitAll()
				        .antMatchers(HttpMethod.GET, "/api/item/allItems", "/api/product/allProducts", "/api/user/users").permitAll()
				        .antMatchers(HttpMethod.GET, "/api/item/id/{id}/quantity/{quantity}"
				        , "/api/product/id/{id}", "/api/user/users/{id}").hasAnyRole("USER", "ADMIN")
				        .antMatchers("/api/item/**", "/api/product/**", "/api/user/users/**").hasRole("ADMIN")
				        .anyRequest().authenticated().and().cors(cors -> cors.configurationSource(corsConfigurationSource()));
			} catch (Exception e) {
			}
		});
		;
	}

	
	//ANOTAR COMO BEAN, CORS ES PARA EL INTERCAMBIO DE INFORMACION DE DATOS CRUZADOS, ES DECIR PARA INTERCAMBIAR 
	//INFORMACION CON UN FRONTEND
	@Bean
	CorsConfigurationSource corsConfigurationSource(){
    	CorsConfiguration corsConfig= new CorsConfiguration();
    	corsConfig.addAllowedOrigin("*"); //TODOS LOS ORIGENES
    	corsConfig.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "DELETE", "OPTIONS"));
    	corsConfig.setAllowCredentials(true);
    	corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    	
    	UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean= new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	// NECESITA TENER UN MISMO TOKEN QUE EL OUATH PARA AUTENTICARSE
    @Bean
    JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter tokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(jwtKey.getBytes()));
		return tokenConverter;
	}

}
