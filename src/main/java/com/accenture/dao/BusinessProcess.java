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
		lerDiretorio(path);
		

//		List<String> listaParaAnalisar = Files.readAllLines(Path.of(fullPath));
//		processFile(listaParaAnalisar);
	}
	
	private static List<String> processedFiles = new ArrayList<String>();
	
	private static void lerDiretorio(String path) {
		String fullPath = path + "\\HOMEPATH\\data\\in";
		File arquivos[];
		File diretorio = new File(fullPath);
		arquivos = diretorio.listFiles();
		
		for(int i = 0; i < arquivos.length; i++){
			/** Caso o arquivo não tenha sido processado, iremos processar */
			if(!processedFiles.contains(arquivos[i].getName())) {
				processFile(fullPath, arquivos[i].getName());
			}
		}
	}
	
	/**
	 * Processar o arquivo e inserir em na lista de arquivos processados. 
	 * 
	 * @param path Caminho de onde o arquivo se encontra
	 * @param fileName Nome do Arquivo
	 */
	private static void processFile(String path, String fileName) {
		try {
			System.out.println("Processando o arquivo: " + fileName);
			processedFiles.add(fileName);
			String fullPath = path + "\\" + fileName;
			List<String> linhasDadosCompra = Files.readAllLines(Path.of(fullPath));		
			for (String linha : linhasDadosCompra) {
				processData(linha);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void processData(String dado) {
		String [] colunas = dado.split("ç");
		int idData = Integer.parseInt(colunas[0]);
		try {
			switch(idData) {
				case 001:{
					System.out.println("sou um vendedor!");
					break;
				}
				case 002:{
					System.out.println("sou um cliente!");
					break;
				}
				case 003:{
					System.out.println("sou uma transação!");
					break;
				}
				
				default:{
					System.err.println("Arquivo sem identificador válido");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
