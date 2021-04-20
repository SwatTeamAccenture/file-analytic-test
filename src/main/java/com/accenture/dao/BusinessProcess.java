package com.accenture.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.accenture.entidades.Data;

public class BusinessProcess {
	
	List<Data> listaBruto = new ArrayList<Data>();
	
	public static void startProcess(String path) throws IOException {
//		String fullPath = path + "\\HOMEPATH\\data\\in\\teste1.txt";
		String fullPath = path + "\\HOMEPATH\\data\\in";
		
		lerDiretorio(fullPath);
		

//		List<String> listaParaAnalisar = Files.readAllLines(Path.of(fullPath));
//		processFile(listaParaAnalisar);
	}
	
	private static void lerDiretorio(String path) {
		File arquivos[];
		File diretorio = new File(path);
		arquivos = diretorio.listFiles();
		
		for(int i = 0; i < arquivos.length; i++){
			System.out.println(arquivos[i].getName());
			
			/**
			 * Caso o arquivo não tenha sido processado, 
			 * processar arquivo, e inserir em lista de arquivos processados.
			 */
			if() {
				
			} else {
				
			}
		}
	}
	
	
	private static void processFile(List<String> linhasDadosCompra) {
		for (String linha : linhasDadosCompra) {
			processData(linha);
		}
	}

	private static void processData(String dado) { //		int idData;
		String [] colunas = dado.split("ç");
		int idData = Integer.parseInt(colunas[0]);
		try {
			switch(idData) {
				case 001:{
					System.out.println("sou um vendedor!");//					System.exit(0);
					break;
				}
				case 002:{
					System.out.println("sou um cliente!");//					System.exit(0);
					break;
				}
				case 003:{
					System.out.println("sou uma transação!");//					System.exit(0);
					break;
				}
				
				default:{//					in.reset();
					System.err.println("você digitou uma opção inválida!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
