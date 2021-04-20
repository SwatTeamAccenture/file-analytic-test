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
	
	private static String FULL_PATH_IN = "";
	private static String FULL_PATH_OUT = "";
	private static String FULL_PATH_LOGGER = "";
	
	public static void startProcess(String path) throws IOException {		
		setPaths(path);
		lerDiretorio();
	}
	
	private static void setPaths(String path) {
		FULL_PATH_IN = path + "\\HOMEPATH\\data\\in";
		FULL_PATH_OUT = path + "\\HOMEPATH\\data\\out";
		FULL_PATH_LOGGER = path + "\\HOMEPATH\\data\\logger";
	}
	
	private static List<String> processedFiles = new ArrayList<String>();
	
	private static void lerDiretorio() {
		File arquivos[];
		File diretorio = new File(FULL_PATH_IN);
		arquivos = diretorio.listFiles();
		
		for(int i = 0; i < arquivos.length; i++){
			/** Caso o arquivo não tenha sido processado, iremos processar */
			if(!processedFiles.contains(arquivos[i].getName())) {
				processFile(arquivos[i].getName());
			}
		}
	}
	
	/**
	 * Processar o arquivo e inserir em na lista de arquivos processados. 
	 * 
	 * @param path Caminho de onde o arquivo se encontra
	 * @param fileName Nome do Arquivo
	 */
	private static void processFile(String fileName) {
		try {
			System.out.println("Processando o arquivo: " + fileName);
			processedFiles.add(fileName);
			String fullPath = FULL_PATH_IN + "\\" + fileName;
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
					sellerLogic(colunas);
					break;
				}
				case 002:{
					customerLogic(colunas);
					break;
				}
				case 003:{
					transactionLogic(colunas);
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
	
	private static void sellerLogic(String[] seller) {
		System.out.println("sou um vendedor!");		
	}
	
	private static void customerLogic(String[] customer) {
		System.out.println("sou um cliente!");		
	}
	
	private static void transactionLogic(String[] transaction) {
		System.out.println("sou uma transação!");		
	}
	
	
	private static void generateRalatory() {
		System.out.println("Gerado relatório");	
	}
	
	private static void generateLogger() {
		System.out.println("Gerado relatório LOGGER_ERRO");
	}
}
