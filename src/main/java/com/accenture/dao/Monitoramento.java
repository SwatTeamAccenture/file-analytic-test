package com.accenture.dao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Monitoramento {

	private static boolean PARAR_MONITORAMENTO = false;
	
	public static void iniciarMonitoramento(String path) {
		try {
			janelaDeProcessamento();
			while (PARAR_MONITORAMENTO == false) {
				monitorarPasta();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Painel para parar encerrar o monitoramento.
	 */
	private static void janelaDeProcessamento() {
		JFrame f=new JFrame("Button Example");  
	    JButton b=new JButton("Parar Monitoramento");  
	    b.setBounds(50,100,200,30);  
	    b.addActionListener(
    		new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
					PARAR_MONITORAMENTO = true;
				    f.setVisible(false);  
		        }  
		    }
	    );  
	    
	    f.add(b);
	    f.setSize(400,400);  
	    f.setLayout(null);  
	    f.setVisible(true);  
	}
	
	
	private static void monitorarPasta() {
		System.out.println("pasta sendo monitorada");
	}
}
