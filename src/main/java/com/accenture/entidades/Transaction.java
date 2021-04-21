package com.accenture.entidades;

import java.util.List;

public class Transaction {
	private int id;
	private Long saleid;
	private List<ListaItems> listaItems;
	private String vendedor;
	private double total;
	
	
	public Transaction(String[] transaction) {
		this.id = Integer.parseInt(transaction[0]);
		this.saleid = Long.parseLong(transaction[1]);
		this.listaItems = processItems(transaction[2]);
		this.vendedor = transaction[3];
	}
	
	private List<ListaItems> processItems(String items) {
		List<ListaItems> listaItems = null;
		
		// Remover o primeiro e último item da string
		StringBuilder sb = new StringBuilder(items);
		sb.delete(items.length() - 1, items.length());
		sb.delete(0, 1);
		String itemsString = sb.toString();
		
		// Separar os items
		String [] item = itemsString.split(",");
		
		// Zerar o total
		setTotal(0);
		for (int i = 0; i < item.length; i++) {
			setTotal(item[i]);
		}
		return listaItems;
	}
	
	// Processar os items e calcular o total
	public void setTotal(String totalToProcess) {
		
		ListaItems list = new ListaItems(totalToProcess);		
		this.total += list.getPrice() * list.getQuantity();
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getSaleid() {
		return saleid;
	}
	public void setSaleid(Long saleid) {
		this.saleid = saleid;
	}
	public List<ListaItems> getListaItems() {
		return listaItems;
	}
	public void setListaItems(List<ListaItems> listaItems) {
		this.listaItems = listaItems;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
}
