package com.proyectspringeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ProyectSpringEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectSpringEurekaApplication.class, args);
	}

}
