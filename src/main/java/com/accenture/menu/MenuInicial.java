package com.accenture.menu;

import java.util.Scanner;

import com.accenture.services.Diretorio;
import com.accenture.services.Monitoramento;

public class MenuInicial {
	
	private static String PATH_DIRETORIO = "C:\\Users\\Micro\\Desktop\\teste";
	public static void iniciarMenu() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Menu Inicial\n"
					+ "Digite uma das opções a seguir.\n"
					+ "0 - Encerrar Programa\n"
					+ "1 - Iniciar Monitoramento\n"
					+ "2 - Escolher/Alterar diretório");

			int condicao = in.nextInt();
			switch(condicao) {
				case 0:{
					System.out.println("Programa encerrado!");
					System.exit(0);
					break;
				}
				case 1:{
					in.reset();
					Monitoramento.iniciarMonitoramento(PATH_DIRETORIO);
					iniciarMenu();
					break;
				}
				case 2:{
					in.reset();
					changeDirectory();
					// C:\Users\Micro\Desktop\teste_2
					break;
				}
				default:{
					in.reset();
					System.err.println("você digitou uma opção inválida!");
					iniciarMenu();
					break;
				}
			}

		} catch (Exception e) {
			System.err.println("Você digitou um valor que não é um número!");
			iniciarMenu();
		}
	}
	
	private static void changeDirectory() {
		
		String NEW_PATH_DIRETORIO = Diretorio.trocarDiretorio();
		
		if (!NEW_PATH_DIRETORIO.equals(PATH_DIRETORIO) && NEW_PATH_DIRETORIO.length() > 0) {
			setPATH_DIRETORIO(NEW_PATH_DIRETORIO);
		}
		
		iniciarMenu();
	}
	
	
	public static String getPATH_DIRETORIO() {
		return PATH_DIRETORIO;
	}
	private static void setPATH_DIRETORIO(String pATH_DIRETORIO) {
		PATH_DIRETORIO = pATH_DIRETORIO;
	}
	
	
}
