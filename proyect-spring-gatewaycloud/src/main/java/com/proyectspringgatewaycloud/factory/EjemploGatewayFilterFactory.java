package com.proyectspringgatewaycloud.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory
		extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

	private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

//siempre poner el constructor
	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		return (exchange, chain) -> {

			logger.info("Ejecutando filtro pre -> msj: " + config.message);
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				logger.info("Ejecutando filtro post -> msj: " + config.message);

				Optional.ofNullable(config.tokenValor).ifPresent(valor -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.tokenNombre, valor).build());
				});
			}));
		};  //  new OrderedGatewayFilter( , 2); // poner el orden del filtro
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("message", "tokenNombre", "tokenValor"); // nueva forma para mandar/recibir argumentos
	}

	// estos metodos (arriba y abajo override) reciben informacion desde el config,
	// y lo retornan dando el valor
	// deseado a la clase
	@Override
	public String name() {
		return "EjemploCookie1";
	}

	public static class Configuracion {
		private String message;
		private String tokenNombre;
		private String tokenValor;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getTokenNombre() {
			return tokenNombre;
		}

		public void setTokenNombre(String tokenNombre) {
			this.tokenNombre = tokenNombre;
		}

		public String getTokenValor() {
			return tokenValor;
		}

		public void setTokenValor(String tokenValor) {
			this.tokenValor = tokenValor;
		}
	}

}
