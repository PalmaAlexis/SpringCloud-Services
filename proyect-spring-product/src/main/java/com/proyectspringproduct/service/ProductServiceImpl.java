package com.proyectspringproduct.service;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectspringproduct.dao.ProductDAO;
import com.commons.model.Product;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private ProductDAO dao;

	@Transactional(readOnly = true)
	@Override
	public List<Product> findAll() {
		return (List<Product>) dao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Product findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Product createProduct(Product p) {
		return dao.save(p);
	}

	@Transactional
	@Override
	public void deleteProductById(Long id) {
		dao.deleteById(id);
	}

}
