package ar.edu.uno.poo1.tp2.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ar.edu.uno.poo1.tp2.modelo.Clave;
import ar.edu.uno.poo1.tp2.modelo.Cromosoma;
import ar.edu.uno.poo1.tp2.modelo.Poblacion;

public class Main {

	public static void main(String[] args) {
		
		Clave.getInstancia().setClave("1AC7D9B273");

//		Poblacion poblacion = new Poblacion(10);
//		poblacion.agregarCromosoma(new Cromosoma("FD47DD66B4"));
//		poblacion.agregarCromosoma(new Cromosoma("432C00CF81"));
//		poblacion.agregarCromosoma(new Cromosoma("0E4653C62F"));
//		poblacion.agregarCromosoma(new Cromosoma("E0E32BE9D5"));
//		poblacion.agregarCromosoma(new Cromosoma("11CA0965A7"));
//		poblacion.agregarCromosoma(new Cromosoma("62D2BD1CE5"));
//		poblacion.agregarCromosoma(new Cromosoma("646D92D80B"));
//		poblacion.agregarCromosoma(new Cromosoma("70691000B6"));
//		poblacion.agregarCromosoma(new Cromosoma("BC6C61D3EE"));
//		poblacion.agregarCromosoma(new Cromosoma("3BADB19C7E"));
		
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String linea = null;
		Poblacion poblacion = null;
		try {
			fileReader = new FileReader("src/ar/edu/uno/poo1/resources/in/Lotedeprueba1.in");
			bufferedReader = new BufferedReader(fileReader);
			linea = bufferedReader.readLine();
			Clave.getInstancia().setClave(linea);
			poblacion = new Poblacion(Integer.parseInt(bufferedReader.readLine()));
			while ((linea = bufferedReader.readLine()) != null) {
				poblacion.agregarCromosoma(new Cromosoma(linea));
			}
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		try {
			if (bufferedReader != null)
				bufferedReader.close();
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		
		poblacion.evolucion();	
	}		
}