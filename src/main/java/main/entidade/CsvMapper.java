package main.entidade;

/**
 * Classe auxiliar para mapear as colunas encontradas nas linhas do CSV para uma classe orientada a objetos
 */
public class CsvMapper {

	private String identificador;
	private String coluna1;
	private String coluna2;
	private String coluna3;
	
	public CsvMapper() {
	}
	
	public CsvMapper(String identificador, String coluna1, String coluna2, String coluna3) {
		super();
		this.identificador = identificador;
		this.coluna1 = coluna1;
		this.coluna2 = coluna2;
		this.coluna3 = coluna3;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getColuna1() {
		return coluna1;
	}
	public void setColuna1(String coluna1) {
		this.coluna1 = coluna1;
	}
	public String getColuna2() {
		return coluna2;
	}
	public void setColuna2(String coluna2) {
		this.coluna2 = coluna2;
	}
	public String getColuna3() {
		return coluna3;
	}
	public void setColuna3(String coluna3) {
		this.coluna3 = coluna3;
	}

	@Override
	public String toString() {
		return "CsvMapper [identificador=" + identificador + ", coluna1=" + coluna1 + ", coluna2=" + coluna2
				+ ", coluna3=" + coluna3 + "]";
	}
	
	
	
}
