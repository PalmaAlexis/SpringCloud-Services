package com.proyectspringitem.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.commons.model.Product;

@FeignClient(name = "app-product")
public interface ItemFeign {
	
	@GetMapping("/allProducts")
	public List<Product> allProducts();
	
	@GetMapping("/id/{id}")
	public Product findProductById(@PathVariable Long id);
	
	@PostMapping("/create")
	public Product createProduct(@RequestBody Product p);
	
	@PutMapping("/update/{id}")
	public Product updateProduct(@RequestBody Product p, @PathVariable Long id );
	
	@DeleteMapping("/delete/{id}")
	public void deleteProduct(@PathVariable Long id);
}
