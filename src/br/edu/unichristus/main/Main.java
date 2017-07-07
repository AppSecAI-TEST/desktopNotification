package br.edu.unichristus.main;

import java.awt.AWTException;

import br.edu.unichristus.views.InterfaceGraficaPrincipal;

public class Main {

	public static void main(String[] args) {
		try {
			new InterfaceGraficaPrincipal();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
