package com.proyectspringitem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectspringitem.clients.ItemFeign;
import com.proyectspringitem.model.Item;
import com.commons.model.Product;

@Service("ItemServiceFeign")
public class ItemServiceFeign implements IItemService {

	@Autowired
	private ItemFeign feign;

	@Override
	public List<Item> findAll() {
		return feign.allProducts().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(feign.findProductById(id), quantity);
	}

	@Override
	public Product createProduct(Product p) {
		return feign.createProduct(p);
	}

	@Override
	public Product updateProduct(Product p, Long id) {
		return feign.updateProduct(p, id);
	}

	@Override
	public void deleteProduct(Long id) {
		feign.deleteProduct(id);
	}


}
