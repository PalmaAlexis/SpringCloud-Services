package com.proyectspringitem.model;
import com.commons.model.Product;

public class Item {

	private Product product;
	private Integer quantity;

	public Item() {
	}

	public Item(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotal() {
		return quantity.doubleValue() * product.getPrice();
	}

}
