package com.proyectspringitem;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	
	@Bean("restTemplate")
	@LoadBalanced
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}
	
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom().slidingWindowSize(10).failureRateThreshold(50)
							.waitDurationInOpenState(Duration.ofSeconds(10L)).permittedNumberOfCallsInHalfOpenState(5)
							.slowCallRateThreshold(50).slowCallDurationThreshold(Duration.ofSeconds(2L))
							.build()) //llamadas lentas //tiempo para que se considere llamada lenta, porcentaje para
					                  //mandar a abierto 
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build())
					.build();  //time out (maximo tiempo para hacer la solicitud)
		});
	}

}
