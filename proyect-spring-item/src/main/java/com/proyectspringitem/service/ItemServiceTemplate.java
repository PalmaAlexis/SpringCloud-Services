package com.proyectspringitem.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.proyectspringitem.model.Item;
import com.commons.model.Product;

@Service("ItemServiceTemplate")
public class ItemServiceTemplate implements IItemService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Item> findAll() {
		List<Product> list = Arrays
				.asList(restTemplate.getForObject("http://app-product/allProducts", Product[].class));
		return list.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		Map<String, String> pathVariable = new HashMap<>();
		pathVariable.put("id", id.toString());
		Product p = restTemplate.getForObject("http://app-product/id/{id}", Product.class, pathVariable);
		return new Item(p, quantity);
	}

	@Override
	public Product createProduct(Product p) {
		HttpEntity<Product> body = new HttpEntity<Product>(p);
		ResponseEntity<Product> response = restTemplate.exchange("http://app-product/create", HttpMethod.POST, body,
				Product.class);
		Product pp = response.getBody();
		return pp;
	}

	@Override
	public Product updateProduct(Product p, Long id) {
		HttpEntity<Product> body= new HttpEntity<Product>(p);
		Map<String, String> pathVariables= new HashMap<>();
		pathVariables.put("id", id.toString());
		ResponseEntity<Product> response = restTemplate.exchange("http://app-product/update/{id}", HttpMethod.PUT
				, body,Product.class, pathVariables);
		return response.getBody();
	}

	@Override
	public void deleteProduct(Long id) {
		Map<String, String> pathVariable= new HashMap<>();
		pathVariable.put("id", id.toString());
		restTemplate.delete("http://app-product/delete/{id}", pathVariable);

	}

}
