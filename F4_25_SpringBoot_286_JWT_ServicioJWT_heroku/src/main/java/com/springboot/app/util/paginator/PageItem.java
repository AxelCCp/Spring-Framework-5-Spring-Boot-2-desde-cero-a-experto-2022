package com.springboot.app.util.paginator;

//CLASE PARA REPRESENTAR CADA UNA DE LAS PÁGINAS. PARA QUE TENGA SU NÚMERO DE PÁGINA Y QUE TIENE UN ATRIBUTO PARA SABER SI ES O NO, PÁGINA ACTUAL.

public class PageItem {
	
	public PageItem(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}
	
	public int getNumero() {
		return numero;
	}
	public boolean isActual() {
		return actual;
	}



	private int numero;          //INDICA EL N° DE PÁGINA.
	private boolean actual;      //INDICA SI ES O NO ES LA PÁGINA ACTUAL.
	
}
