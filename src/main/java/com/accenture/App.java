package com.accenture;

//import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

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
