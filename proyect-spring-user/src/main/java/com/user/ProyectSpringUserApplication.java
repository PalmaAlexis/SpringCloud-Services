package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EntityScan("com.usercommons.model.entity")
@EnableEurekaClient
public class ProyectSpringUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectSpringUserApplication.class, args);
	}

}
