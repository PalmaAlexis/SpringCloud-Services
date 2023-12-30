package com.proyectspringitem.service;

import java.util.List;
import com.proyectspringitem.model.Item;
import com.commons.model.Product;

public interface IItemService {

	public List<Item> findAll();

	public Item findById(Long id, Integer quantity);

	public Product createProduct(Product p);
	
	public Product updateProduct(Product p, Long id); //aqui si ponemos update porque aqui si va ser parte 
	//de las acciones del servicio, y en product no lo ponemos porque lo podemos hacer con demas acciones del mismo
	
	//si aqui lo hacemos como antes, de solo en el controlador, no se haria a nivel de producto, solo de item
	
	//el update esta aqui porque es parte de consumir el api rest, por lo mismo, porque producto tiene el metodo
	//putmapping en su controlador!!!, y el product no lo tiene en su servicio porque se puede hacer con los demas
	//metodos!!!
	
	public void deleteProduct(Long id);

}
