package com.proyectspringproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.commons.model"})
public class ProyectSpringProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectSpringProductApplication.class, args);
	}

}
