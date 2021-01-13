import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Clase que se ocupara de hacer el scrap y escribir en el fichero
 */
public class Scraper {

	private int posicionNombre;
	private int posicionFecha;
	private int posicionHora;
	private int posicionUltimo;
	private StringBuilder rowBuilder;

	/**
	 * Constructor 
	 */
	public Scraper() {
		posicionNombre = 0;
		posicionFecha = 0;
		posicionHora = 0;
		posicionUltimo = 0;
		//Creamos un string builder para construir lo que pillemos de la web
	    rowBuilder = new StringBuilder();
	}
	
	/**
	 * Método para ir a la pagina y pillar la info que queremos 
	 */
	public void scrapear() {
	//Guardamos en el doc el html de la dirección que le hemos puesto 
	    Document doc = null;
	    try {
	        doc = Jsoup.connect(
	                "https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice")
	                .get();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    //Cuando tenemos el html entero, buscamos el id(#) de la tabla que queremos pillar y el primer elemento
	    Element table = doc.select("#ctl00_Contenido_tblÍndice").first();
	    
	    //Accedemos al cuerpo de la tabla 
	    Element tbody = table.getElementsByTag("tbody").first();
	    
	   
	    
	    //Recorremos todos los tr que hay en nuestra tabla 
	    for (Element tr : tbody.getElementsByTag("tr")) { 
	        
	    	
			//Por cada tr le preguntamos si tiene th (Cabezera) y recorremos todo lo que contenga 
	    	  for (Element th : tr.getElementsByTag("th")) {
	              
	              switch(th.text()) {
	              
	              case "Nombre":
	              
	                  posicionNombre = th.elementSiblingIndex();
	                  break;
	              case "Fecha":
	                
	                  posicionFecha = th.elementSiblingIndex();
	                  break;
	              case "Último":
	                  
	            	  posicionUltimo = th.elementSiblingIndex();
	            	  break;
	              case "Hora":
	                  
	            	  posicionHora = th.elementSiblingIndex();
	            	  break;
	              }
	
	          }
	    	  //Metemos todos los td en una lista para poder coger los que queremos
	    	  Elements tds = tr.getElementsByTag("td"); 
	    	    
    	  	//Aquí le decimos que segun la posicion que hemos pillado antes que nos lo meta en el rowBuilder
    	    if (tds.size() > 0){
    	    	rowBuilder.append(tds.get(posicionFecha).text()+";");
    	    	rowBuilder.append(tds.get(posicionHora).text()+";");
    	     rowBuilder.append(tds.get(posicionNombre).text()+";");
    	     rowBuilder.append(tds.get(posicionUltimo).text());
    	      }
    	    tds = null;
	    } 
    
	}
	/**
	 * Método para escribir la info en nuestro fichero txt
	 */
	public void escribirFichero() {

	    //Imprimimos en pantalla lo que tenemos 
	    System.out.println(rowBuilder.toString());
	    
	    //Escribimos en el fichero lo que tenemos en el rowBuilder
	    BufferedWriter escritor;
	    try {
	        escritor = new BufferedWriter(new FileWriter(new File("fichero.txt"),true));
	        escritor.append(rowBuilder);
	        escritor.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    rowBuilder = null;
	}
}
