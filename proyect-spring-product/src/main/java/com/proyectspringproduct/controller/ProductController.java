package com.proyectspringproduct.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.commons.model.Product;
import com.proyectspringproduct.service.IProductService;

@RestController
public class ProductController{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private IProductService service;
	
	@GetMapping("/allProducts")
	public List<Product> allProducts(){
		return service.findAll().stream().map(p-> {
			p.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
			return p;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/id/{id}")
	public Product findProductById(@PathVariable Long id) throws InterruptedException {
		
		/*
		if(id.equals(10L)) {
			throw new IllegalArgumentException("Producto no encontrado");
		}
		
		if(id.equals(2L)) {
			TimeUnit.SECONDS.sleep(2L);
		}*/
		
		Product p= service.findById(id);
		p.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return p; 
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProduct(@RequestBody Product p) {
		return service.createProduct(p);
	}
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product updateProduct(@RequestBody Product p, @PathVariable Long id ) {
		Product pDb= service.findById(id);   //el update lo hacemos a nivel del controlador y no del servicio 
		pDb.setName(p.getName());  
		pDb.setBrand(p.getBrand());
		pDb.setPrice(p.getPrice());
		return service.createProduct(pDb);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long id) {
		service.deleteProductById(id);
	}

}
