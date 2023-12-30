package com.proyectspringproduct.dao;
import org.springframework.data.repository.CrudRepository;
import com.commons.model.Product;

public interface ProductDAO extends CrudRepository<Product, Long>{
}
