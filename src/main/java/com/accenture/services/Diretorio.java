package com.accenture.services;

public class Diretorio {
	
	/**
	 * Trocar o path.
	 * 
	 * @return string com o caminho alterado para onde estará a pasta: 'HOMEPATH'
	 */
	public static String trocarDiretorio() {

		String newPath = "";
		
		try {
			newPath = com.accenture.menu.CallScanner.getUserInput("caminho de onde encontraremos a pasta 'HOMEPATH'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newPath;
	}
}
