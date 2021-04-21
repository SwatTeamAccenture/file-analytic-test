package com.accenture.entidades;

public class ListaItems {
	private int id;
	private int quantity;
	private double price;
	
	
	
	public ListaItems(String totalToProcess) {
		String [] item = totalToProcess.split("-");
		this.id = Integer.parseInt(item[0]);
		this.quantity =  Integer.parseInt(item[1]);
		this.price = Double.parseDouble(item[2]);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	
}
