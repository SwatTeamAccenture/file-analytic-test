package main.entidade;

public class Cliente {
	private String cnpj;
	private String nome;
	private String negocio;
	
	public Cliente(String cnpj, String nome, String negocio) {
		super();
		this.cnpj = cnpj;
		this.nome = nome;
		this.negocio = negocio;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNegocio() {
		return negocio;
	}
	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}
	
	
	
	
	
}
