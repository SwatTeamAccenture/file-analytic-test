package com.accenture;

import com.accenture.menu.MenuInicial;

public class App {
	public static void main(String[] args) {
		 
		
		try {		
			MenuInicial.iniciarMenu();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
