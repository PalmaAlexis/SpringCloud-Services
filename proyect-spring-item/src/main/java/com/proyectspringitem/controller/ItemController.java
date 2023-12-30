package com.proyectspringitem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyectspringitem.model.Item;
import com.commons.model.Product;
import com.proyectspringitem.service.IItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope //para que refresquemos, ya que los componentes inyectados son de tipo singleton
@RestController
public class ItemController {

	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private Environment env;

	@Value("${configuration.texto}")
	private String texto;

	@SuppressWarnings("rawtypes")
	@Autowired
	private CircuitBreakerFactory cbFactory;

	@Autowired
	@Qualifier("ItemServiceFeign")
	private IItemService service;

	@GetMapping("/allItems")
	public List<Item> allItems(@RequestParam(name = "nombre", required = false) String name,
			@RequestHeader(name = "token-request", required = false) String token) {
		// System.out.println(name);
		// System.out.println(token);
		return service.findAll();
	}

	// @HystrixCommand(fallbackMethod = "findItemByIdFB")
	@GetMapping("/id/{id}/quantity/{quantity}")
	public Item findItemById(@PathVariable Long id, @PathVariable Integer quantity) {
		return cbFactory.create("items").run(() -> service.findById(id, quantity),
				e -> findItemByIdFB(id, quantity, e));
	}

	@CircuitBreaker(name = "items", fallbackMethod = "findItemByIdFB")
	@GetMapping("/id2/{id}/quantity/{quantity}")
	public Item findItemById2(@PathVariable Long id, @PathVariable Integer quantity) {
		return service.findById(id, quantity);
	}

	@CircuitBreaker(name = "items", fallbackMethod = "findItemByIdFB2")
	@TimeLimiter(name = "items") // el fallbackmethod solo se aplica al circuitbreaker, ya que es el que manda al
									// corto
	@GetMapping("/id3/{id}/quantity/{quantity}")
	public CompletableFuture<Item> findItemById3(@PathVariable Long id, @PathVariable Integer quantity) {
		return CompletableFuture.supplyAsync(() -> service.findById(id, quantity));
	}

	// lo que aqui pasa es que el circuit breaker solo se limita a hacer este mismo
	// (3 etapas),
	// y solo maneja el tiempo, conforme a las llamadas lentas, pero no configura un
	// time out

	// por otro lado la anotacion time limiter, solo se limita a hacer el time out,
	// si bien puede dar o mandar
	// un fallbackmethod si lo hace pero solo cuando hay un timeout, no cuando se
	// abre el circuito

	// para hacer estas dos funcionalidades juntas, como la funcion que hacia el
	// factory (el poder mandar a circuit
	// breaker con la opcion de hacerlo por timeout) tenemos que fusionar (juntar)
	// las anotaciones //circuitbreaker y
	// time limiter@@@@@
	public Item findItemByIdFB(@PathVariable Long id, @PathVariable Integer quantity, Throwable e) {
		logger.info(e.getMessage());
		Product p = new Product();
		p.setName("PRODUCT BACK");
		p.setBrand("BRAN BACK");
		p.setPrice(0.0);
		return new Item(p, quantity);
	}

	// completableFuture es de timeLimiter
	public CompletableFuture<Item> findItemByIdFB2(@PathVariable Long id, @PathVariable Integer quantity, Throwable e) {
		logger.info(e.getMessage());
		Product p = new Product();
		p.setName("PRODUCT BACK");
		p.setBrand("BRAN BACK");
		p.setPrice(0.0);
		return CompletableFuture.supplyAsync(() -> {
			return new Item(p, quantity);
		});
	}

	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) { // comprobar estamos en
			json.put("autor.nombre", env.getProperty("configuration.autor.nombre"));																					// dev
			json.put("autor.email", env.getProperty("configuration.autor.email"));
		}
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProduct(@RequestBody Product p) {
		return service.createProduct(p);
	}
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product updateProduct(@RequestBody Product p, @PathVariable Long id) {
		return service.updateProduct(p, id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long id) {
		service.deleteProduct(id);
	}

}
