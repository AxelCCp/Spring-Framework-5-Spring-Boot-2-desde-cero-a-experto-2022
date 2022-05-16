package com.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

//1.- SE OBTIENE EL N° DE ELEMENTOS POR PÁGINA.
//2.- E OBTIENE EL TOTAL DE PÁGINAS.
//3.-SE REALIZA EL CÁLCULO DEL DESDE Y EL HASTA, DE LAS PÁGINAS DEL PAGINADOR. 
	//SI EL TOTAL DE PÁGINAS ES <= AL N° DE ELEMENTOS POR PÁGINA, ENTONCES SE MOSTRARÁ EL PAGINADOR COMPLETO. DESDE LA 1RA A LA ULTIMA PÁGINA. 
//3.1.- SE OBTIENE PÁGINA ACTUAL.


//4.- SE RECORREN LAS PÁGINAS VACÍAS Y SE LLENAN CON CADA UNO DE LOS ITEMS CON SU RESPECTIVO NÚMERO. Y SE DEFINE SI ES ACTUAL O NO.
//5.- MÉTODOS GET PARA PODER ACCEDER DESDE LA VISTA A LAS DIFERENTES PÁGINAS.
//6.- OTROS MÉTODOS PARA SABER SI TIENE PAGINAS DELANTE O ATRÁS.
public class PageRender <T> {
	
	public PageRender(String url, Page<T> page) {
		
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();  //ARRAY CON LAS PAGINAS Y SUS NÚMEROS.
		//1
		numeroElementosPorPagina = page.getSize();
		//2
		totalDePaginas = page.getTotalPages();
		//3.1
		paginaActual = page.getNumber() + 1;
		
		int desde, hasta;
		
		//3
		if(totalDePaginas <= numeroElementosPorPagina) {
			desde = 1;
			hasta = totalDePaginas;
		}else {
			
			if(paginaActual <= numeroElementosPorPagina/2) {
				desde = 1;
				hasta = numeroElementosPorPagina;
			} else if(paginaActual >= totalDePaginas - numeroElementosPorPagina/2) {
				desde = totalDePaginas - numeroElementosPorPagina + 1;
				hasta = numeroElementosPorPagina;
			} else {
				desde = paginaActual - numeroElementosPorPagina/2;
				hasta = numeroElementosPorPagina;
			}
			
		}
		
		//4
		for(int i=0; i<hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}
	}
	
	//5
	public String getUrl() {
		return url;
	}
	public int getTotalDePaginas() {
		return totalDePaginas;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	public List<PageItem> getPaginas() {
		return paginas;
	}


	//6
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevius() {
		return page.hasPrevious();
	}
	

	private String url;  				//
	private Page<T> page;  				//LISTADO PAGINABLE DE LOS CLIENTES.
	private int totalDePaginas;			//PARA REALIZAR EL CÁLCULO DE LA CANTIDAD DE PÁGINAS.
	private int numeroElementosPorPagina; //PARA CALCULAR LA CANTIDAD DE ELEMENTOS POR PÁGINA.
	private int paginaActual;           
	private List<PageItem>paginas;		//UNA COLECCIÓN YA QUE SON VARIAS PÁGINAS.

}
