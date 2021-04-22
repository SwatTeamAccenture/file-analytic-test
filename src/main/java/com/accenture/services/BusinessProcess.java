package com.accenture.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.accenture.entidades.Transaction;

public class BusinessProcess {
	
	private static String PATH = "";
	private static String FILE_NAME = "";
	private static String FILE_PATH = "";
	private static String FULL_PATH_IN = "";
	private static String FULL_PATH_OUT = "";
	private static String FULL_PATH_LOGGER = "";
	
	/** Listas para gerir arquivos e dados processados */
	private static List<String> processedFiles = new ArrayList<String>();
	private static List<String> listaReportLogger = new ArrayList<String>();
	private static List<String> listaReport = new ArrayList<String>();	

	/** Variáveis para cálculo de valores, para gerar relatório */
	private static double VALOR_ID_MAIOR_VENDA = Double.MIN_VALUE;
	private static double VALOR_PIOR_VENDEDOR = Double.MAX_VALUE;
	
	/** Variáveis para gerar de relatório */
	private static int QTD_CLIENTES = 0;
	private static int QTD_VENDEDORES = 0;
	private static int ID_MAIOR_VENDA = 0;
	private static String PIOR_VENDEDOR = "";
	
	/** Flag para controle de verificação de checagem de existencia dos diretórios */
	private static boolean directoriesChecked = false;
	
	public static void startProcess(String path) throws IOException {
		checkPathsAndDirectories(path);
		lerDiretorio();
	}
	
	private static void checkPathsAndDirectories(String path) {
		
		if (PATH != path) {
			setDirectoriesChecked(false);
			setPATH(path);
			processedFiles.clear();
			setFULL_PATH_IN(getPATH() + "\\HOMEPATH\\data\\in");
			setFULL_PATH_OUT(getPATH() + "\\HOMEPATH\\data\\out");
			setFULL_PATH_LOGGER(getPATH() + "\\HOMEPATH\\data\\logger");
		};

		if (!isDirectoriesChecked()) {			
			checkDirectories();
		}
	}
	
	private static void checkDirectories() {
		System.out.println("chegando diretório");
		
		File diretorio_in = new File(FULL_PATH_IN);
		File diretorio_out = new File(FULL_PATH_OUT);
		File diretorio_logger = new File(FULL_PATH_LOGGER);
		
		if (!diretorio_in.exists()) {
			diretorio_in.mkdirs();
		}
		
		if (!diretorio_out.exists()) {
			diretorio_out.mkdirs();
		}
		
		if (!diretorio_logger.exists()) {
			diretorio_logger.mkdirs();
		}
		
		setDirectoriesChecked(true);
		
	}
	
	
	private static void lerDiretorio() {
		
		File arquivos[];
		File diretorio = new File(FULL_PATH_IN);
		arquivos = diretorio.listFiles();

		for(int i = 0; i < arquivos.length; i++){
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
			setFILE_NAME(fileName);
			setFILE_PATH(fileName);
			resetReportDataValues();
			
			List<String> listFileData = Files.readAllLines(Path.of(getFILE_PATH()));
			
			for (String data : listFileData) {
				processData(data);
			}
			
			processedFiles.add(fileName);
			
			gerarReport();

			if (listaReportLogger.size() > 0) {
				gerarReportLogger();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	private static void resetReportDataValues() {
		listaReport.clear();
		listaReportLogger.clear();
		setQTD_CLIENTES(0);
		setQTD_VENDEDORES(0);
		setID_MAIOR_VENDA(0);
		setPIOR_VENDEDOR("");
	}
	
	
	private static void gerarReport() {
		try {
			
			String reportFilePath = getFULL_PATH_OUT() + "\\" +  getFILE_NAME().trim().toLowerCase();
			
			listaReport.add("QTD_VENDEDORESçQTD_CLIENTESçID_MAIOR_VENDAçPIOR_VENDEDOR");
			listaReport.add(getQTD_VENDEDORES() + "ç" + getQTD_CLIENTES() + "ç" + getID_MAIOR_VENDA() + "ç" + getPIOR_VENDEDOR());
			Files.write(Path.of(reportFilePath), listaReport);
			
			System.out.println("Reporte Gerado para o arquivo: " + FILE_NAME);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void gerarReportLogger() {
		try {
			
			String reportFilePath = getFULL_PATH_LOGGER() + "\\" +  getFILE_NAME().trim().toLowerCase();
			Files.write(Path.of(reportFilePath), listaReportLogger);
			
			System.err.println("Reporte Logger Gerado para o arquivo: " + getFILE_NAME());
			
			} catch (IOException e) {
				System.out.println("Problemas ao gerar reporte logger!");
				e.printStackTrace();
			}
	}
	
	
	private static void processData(String dado) {
		try {

			String [] colunas = dado.split("ç");
			int idData = Integer.parseInt(colunas[0]);
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
					String infoReport = dado + "çO valor " + colunas[0] + "não é monitorado";
					listaReportLogger.add(infoReport);
					break;
				}
			}
		} catch (Exception e) {
			listaReportLogger.add(dado +"ç"+ e.getMessage());
		}
	}

	private static void sellerLogic(String[] seller) {
		try {
			
			setQTD_VENDEDORES(getQTD_VENDEDORES() + 1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void customerLogic(String[] customer) {
		try {

			setQTD_CLIENTES(getQTD_CLIENTES() + 1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void transactionLogic(String[] transaction) {
		try {
			
			Transaction tran = new Transaction(transaction);
			
			if (tran.getTotal() > getVALOR_ID_MAIOR_VENDA()) {
				setID_MAIOR_VENDA(tran.getSaleid());
				setVALOR_ID_MAIOR_VENDA(tran.getTotal());
			}
			
			if (tran.getTotal() < getVALOR_PIOR_VENDEDOR()) {
				setPIOR_VENDEDOR(tran.getVendedor());
				setVALOR_PIOR_VENDEDOR(tran.getTotal());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/** Getters e Setters */
	
	public static int getQTD_CLIENTES() {
		return QTD_CLIENTES;
	}

	public static double getVALOR_ID_MAIOR_VENDA() {
		return VALOR_ID_MAIOR_VENDA;
	}

	public static void setVALOR_ID_MAIOR_VENDA(double vALOR_ID_MAIOR_VENDA) {
		VALOR_ID_MAIOR_VENDA = vALOR_ID_MAIOR_VENDA;
	}

	public static double getVALOR_PIOR_VENDEDOR() {
		return VALOR_PIOR_VENDEDOR;
	}

	public static void setVALOR_PIOR_VENDEDOR(double vALOR_PIOR_VENDEDOR) {
		VALOR_PIOR_VENDEDOR = vALOR_PIOR_VENDEDOR;
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

	public static String getFILE_NAME() {
		return FILE_NAME;
	}

	public static void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public static String getFULL_PATH_IN() {
		return FULL_PATH_IN;
	}

	public static void setFULL_PATH_IN(String fULL_PATH_IN) {
		FULL_PATH_IN = fULL_PATH_IN;
	}

	public static String getFULL_PATH_OUT() {
		return FULL_PATH_OUT;
	}

	public static void setFULL_PATH_OUT(String fULL_PATH_OUT) {
		FULL_PATH_OUT = fULL_PATH_OUT;
	}

	public static String getFULL_PATH_LOGGER() {
		return FULL_PATH_LOGGER;
	}

	public static void setFULL_PATH_LOGGER(String fULL_PATH_LOGGER) {
		FULL_PATH_LOGGER = fULL_PATH_LOGGER;
	}

	public static String getPATH() {
		return PATH;
	}

	public static void setPATH(String pATH) {
		PATH = pATH;
	}

	public static String getFILE_PATH() {
		return FILE_PATH;
	}

	public static void setFILE_PATH(String fileName) {
		FILE_PATH = getFULL_PATH_IN() + "\\" + fileName;
	}

	public static boolean isDirectoriesChecked() {
		return directoriesChecked;
	}

	public static void setDirectoriesChecked(boolean directoriesChecked) {
		BusinessProcess.directoriesChecked = directoriesChecked;
	}

	public static List<String> getProcessedFiles() {
		return processedFiles;
	}

	public static void setProcessedFiles(List<String> processedFiles) {
		BusinessProcess.processedFiles = processedFiles;
	}

	public static List<String> getListaReportLogger() {
		return listaReportLogger;
	}

	public static void setListaReportLogger(List<String> listaReportLogger) {
		BusinessProcess.listaReportLogger = listaReportLogger;
	}

	public static List<String> getListaReport() {
		return listaReport;
	}

	public static void setListaReport(List<String> listaReport) {
		BusinessProcess.listaReport = listaReport;
	}
	
	
}
