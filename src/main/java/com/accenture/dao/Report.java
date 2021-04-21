package com.accenture.dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Report {
	
	private static List<String> dataReportList = new ArrayList<String>();
	public static void generateReport(List<String> columnTitle, List<String> columnData, String fileName, boolean loggerReport) {
//		String[] columTitle = {"qtd. clientes", "qtd. vendedores", "id maior venda", "pior vendedor"};

		String fullPath = "";//!loggerReport ?  FULL_PATH_OUT + "\\" + fileName + ".txt" : FULL_PATH_LOGGER + "\\" + fileName + ".txt";
//		String fullPath = FULL_PATH_OUT + "\\" + fileName + ".txt";
		try {
			dataReportList.clear();		
			for (String column : columnTitle) {
				dataReportList.add(column);
			}
			for (String dado : columnData) {
				dataReportList.add(dado);			
			}
			
			Files.write(Path.of(fullPath), dataReportList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
