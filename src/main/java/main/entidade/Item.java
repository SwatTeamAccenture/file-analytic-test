package main.entidade;

public class Item {
	private Double id;
	private Integer quantidade;
	private Double preco;
	
	public Item(Double id, Integer quantidade, Double preco) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	public Item() {
	}
	public Double getId() {
		return id;
	}
	public void setId(Double id) {
		this.id = id;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
