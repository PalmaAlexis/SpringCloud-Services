package com.proyectspringgatewaycloud.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilterExample implements GlobalFilter, Ordered{

	private static Logger logger= LoggerFactory.getLogger(GlobalFilterExample.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Ejecutando filtro pre");
		
		//modificando el request   //lo debemos de hacer mutable, ya que el request es inmutable por defecto
		exchange.getRequest().mutate().headers( h -> h.add("token", "12345"));
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("Ejecutanfo filtro post");
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
			//exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
			
			Optional.ofNullable( exchange.getRequest().getHeaders().getFirst("token") ).ifPresent(value -> { 
						exchange.getResponse().getHeaders().add("token", value);			
					});
			//aqui decimos que si esta presente el valor del header "token" en el request, se lo pase al 
			//response 
			//primero estamos añadiendo un header al request , y luego ese valor se lo estamos pasando al response
		}));
	}

	@Override
	public int getOrder() {
		return 10;
	}
	//cuando el valor del orden en negativo, se traduce en que este filtro se va a leer primero que todos los demas 
	//porque como ya dicho tiene el valor negativo, y el filtro post se aplica al ultimo
	//es decir, se aplica al principio (filtro pre) y al final (filtro post)
	//mono from frunnable crea un mono que contiene diferentes parametros!!
	
	//el chain es una cadena, y con el filter ejecuta todos los filtros que corresponden en exchage
	//el then es una vez que se terminen de ejecutar todos los filtros
	//mono from runnable hace que se ejecuten cosas en el mono, por eso se pone en el then para que se ejecuten
	//una vez que ya pasaron todos los filtros
	
	//el chain.filter(exchange) esta diciendo que filtre o que aplique todos los filtros el exchange y una vez que se acaba
	//para eso se ocupa el then
	
	//el exchange es una caja que contiene las respuesta y el request, y ahi se pueden modificar ambas

}
