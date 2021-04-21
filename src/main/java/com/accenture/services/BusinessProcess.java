package com.accenture.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.accenture.entidades.Data;
import com.accenture.entidades.Transaction;
import com.accenture.entidades.Vendedor;

public class BusinessProcess {
	
	List<Data> listaBruto = new ArrayList<Data>();

	private static String FILE_NAME = "";
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
//			System.out.println("Processando o arquivo: " + fileName);
			FILE_NAME = fileName;
			resetDataReportValues();
			processedFiles.add(fileName);
			String fullPath = FULL_PATH_IN + "\\" + fileName;
			List<String> linhasDadosCompra = Files.readAllLines(Path.of(fullPath));
			for (String linha : linhasDadosCompra) {
				processData(linha);
			}
			
			// Gerar relatório
			gerarReport();
		} catch (Exception e) {
			// Gerar Logger se erro
			e.printStackTrace();
		}
	}
	

	private static int QTD_CLIENTES = 0;
	private static int QTD_VENDEDORES = 0;
	private static int ID_MAIOR_VENDA = 0;
	private static String PIOR_VENDEDOR = "";
	
	
	private static void resetDataReportValues() {
		setQTD_CLIENTES(0);
		setQTD_VENDEDORES(0);
		setID_MAIOR_VENDA(0);
		setPIOR_VENDEDOR("");
	}
	private static void gerarReport() {
		try {
		String listaNova = 	FULL_PATH_OUT + "\\" + FILE_NAME.trim().toUpperCase()+".txt";
		
		List<String> listaReport = new ArrayList<String>();
		listaReport.add("QTD_VENDEDORES;QTD_CLIENTES;ID_MAIOR_VENDA;PIOR_VENDEDOR");
		listaReport.add(getQTD_VENDEDORES() + ";" + getQTD_CLIENTES() + ";" + getID_MAIOR_VENDA() + ";" + getPIOR_VENDEDOR());
		
		Files.write(Path.of(listaNova), listaReport);
		System.out.println("Reporte Gerado para o arquivo: " + FILE_NAME);
		} catch (IOException e) {
			System.out.println("Problemas ao gerar reporte!");
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
	
	private static List<String> columnTitle = new ArrayList<String>();
	
	private static void sellerLogic(String[] seller) {
		try {
			int qtdVendedores = getQTD_VENDEDORES() + 1;
			setQTD_VENDEDORES(qtdVendedores);
		} catch (Exception e) {
			e.printStackTrace();
//			Report.generateReport(columnTitle, seller, fileName, loggerReport);
		}
	}
	
	private static void customerLogic(String[] customer) {
		try {
			int qtdClientes = getQTD_CLIENTES() + 1;
			setQTD_CLIENTES(qtdClientes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static double MAIOR_VENDA = Double.MAX_VALUE;
	private static double MENOR_VENDA = Double.MIN_VALUE;
	
	private static void transactionLogic(String[] transaction) {
		try {
			Transaction tran = new Transaction(transaction);
			ID_MAIOR_VENDA = tran.getTotal() > MENOR_VENDA ? tran.getId() : ID_MAIOR_VENDA;
			PIOR_VENDEDOR = tran.getTotal() < MAIOR_VENDA ? tran.getVendedor() : PIOR_VENDEDOR;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getQTD_CLIENTES() {
		return QTD_CLIENTES;
	}

	public static void setQTD_CLIENTES(int qTD_CLIENTES) {
		QTD_CLIENTES = qTD_CLIENTES;
	}

	public static int getQTD_VENDEDORES() {
		return QTD_VENDEDORES;
	}

	public static void setQTD_VENDEDORES(int qTD_VENDEDORES) {
		QTD_VENDEDORES = qTD_VENDEDORES;
	}

	public static int getID_MAIOR_VENDA() {
		return ID_MAIOR_VENDA;
	}

	public static void setID_MAIOR_VENDA(int iD_MAIOR_VENDA) {
		ID_MAIOR_VENDA = iD_MAIOR_VENDA;
	}

	public static String getPIOR_VENDEDOR() {
		return PIOR_VENDEDOR;
	}

	public static void setPIOR_VENDEDOR(String pIOR_VENDEDOR) {
		PIOR_VENDEDOR = pIOR_VENDEDOR;
	}
	
}
