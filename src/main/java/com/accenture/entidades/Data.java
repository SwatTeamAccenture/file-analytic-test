package com.accenture.entidades;

public class Data {
	private int idData;
	public Data(String dado) {
		String [] colunas = dado.split(";");
		this.idData = Integer.parseInt(colunas[0]);
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
