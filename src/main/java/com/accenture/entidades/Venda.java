package com.accenture.entidades;

import java.util.List;

public class Venda {
	private int id;
	private Long saleid;
	private List<ListaItems> listaItems;
	private String vendedor;
	
	
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
