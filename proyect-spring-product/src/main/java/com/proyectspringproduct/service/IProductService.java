package com.proyectspringproduct.service;
import java.util.List;
import com.commons.model.Product;

public interface IProductService {
	
	public List<Product> findAll();
	public Product findById(Long id);
	//implementando
	public Product createProduct(Product p);
	public void deleteProductById(Long id);

}
