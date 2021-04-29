package main.entidade;

import java.util.List;

public class Venda {

	private Double id;
	private List<Item> listaItem;
	private String nomeVendedor;
	
	public Double valorTotalVenda = 0.0;
	
	public Venda(Double id, List<Item> listaItem, String nomeVendedor) {
		super();
		this.id = id;
		this.listaItem = listaItem;
		this.nomeVendedor = nomeVendedor;
	}
	public Double getId() {
		return id;
	}
	public void setId(Double id) {
		this.id = id;
	}
	public List<Item> getListaItem() {
		return listaItem;
	}
	public void setListaItem(List<Item> listaItem) {
		this.listaItem = listaItem;
	}
	public String getNomeVendedor() {
		return nomeVendedor;
	}
	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}
	
	public void calcularValorTotalVenda() {
		for (Item item : listaItem) {
			this.valorTotalVenda = Double.sum(this.valorTotalVenda, item.getPreco() * item.getQuantidade());
		}
	}
	
	
	
	
}
