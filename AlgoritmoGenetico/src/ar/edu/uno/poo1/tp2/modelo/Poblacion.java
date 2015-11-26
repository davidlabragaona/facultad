package ar.edu.uno.poo1.tp2.modelo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import ar.edu.uno.poo1.tp2.modelo.Cromosoma;

public class Poblacion {

	ArrayList<Cromosoma> cromosomas;

	public Poblacion(Integer tamanio) {
		this.setCromosomas(new ArrayList<Cromosoma>(tamanio));
	}

	public void agregarCromosoma(Cromosoma x) {
		cromosomas.add(x);
	}

	public Integer getTamanioPoblacion() {
		return cromosomas.size();
	}

	public ArrayList<Cromosoma> getCromosomas() {
		return cromosomas;
	}

	private void setCromosomas(ArrayList<Cromosoma> cromosomas) {
		this.cromosomas = cromosomas;
	}

	public void evolucion() {

		Integer contador = 0;

		mostrarPoblacion();
		
		while (this.getMayorFitnessPoblacion() < Clave.getInstancia().getCromosoma().getFitness()) {
			
			
			System.out.println("*** Torneo ***");
			//this.torneo();
			//mostrarPoblacion();
			System.out.println("*** Cruce ***");
			this.cruce();
			//mostrarPoblacion();
			System.out.println("*** Mutación ***");
			this.mutacion();
			mostrarPoblacion();
			contador++;
		}

		FileWriter archivo = null;
		PrintWriter pw = null;
		try {
			archivo = new FileWriter("src/ar/edu/uno/poo1/resources/out/Lotedeprueba1.out");
			pw = new PrintWriter(archivo);

			pw.println("La clave fue encontrada");
			pw.println("Cantidad de generaciones: " + contador);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != archivo)
					archivo.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("Finalizó la búsqueda");
	}

	public void torneo() {
		Random generadorAleatorios = new Random(System.currentTimeMillis());
		ArrayList<Cromosoma> pobAux = new ArrayList<Cromosoma>(this.getTamanioPoblacion());
		ArrayList<Cromosoma> pobAux1 = new ArrayList<Cromosoma>(this.getTamanioPoblacion());
		Cromosoma cromosomaaux1;
		Cromosoma cromosomaaux2;
		Integer random1 = 0;
		Integer random2 = 0;
		for (int i = 0; i < this.getTamanioPoblacion(); i++) {
			random1 = generadorAleatorios.nextInt(this.getTamanioPoblacion());
			random2 = generadorAleatorios.nextInt(this.getTamanioPoblacion());
			while (random1 == random2)
				random2 = generadorAleatorios.nextInt(this.getTamanioPoblacion());
			cromosomaaux1 = this.getCromosomas().get(random1);
			cromosomaaux2 = this.getCromosomas().get(random2);

			if (cromosomaaux1.getFitness() > cromosomaaux2.getFitness()) {
				pobAux.add(cromosomaaux1);
			} else {
				pobAux.add(cromosomaaux2);
			}
		}
		
		for (int i = 0; i < this.getTamanioPoblacion(); i++) {
			if (this.getCromosomas().get(i).getFitness() < pobAux.get(i).getFitness())
				pobAux1.add(pobAux.get(i));
			else
				pobAux1.add(this.getCromosomas().get(i));
		}
		this.setCromosomas(pobAux1);
	}

	public void cruce() {
		Random generadorAleatorios = new Random(System.currentTimeMillis());
		Integer centromero = 0;
		Integer random1 = 0;
		Integer random2 = 0;
		String brazoCortoPadre;
		String brazoCortoMadre;
		String brazoLargoPadre;
		String brazoLargoMadre;
		ArrayList<Cromosoma> pobAux = new ArrayList<Cromosoma>(this.getTamanioPoblacion());
		ArrayList<Cromosoma> pobResultante = new ArrayList<Cromosoma>(this.getTamanioPoblacion());

		for (Integer i = 0; i < this.getTamanioPoblacion() / 2; i++) {
			random1 = generadorAleatorios.nextInt(this.getTamanioPoblacion());
			random2 = generadorAleatorios.nextInt(this.getTamanioPoblacion());

			while (random1 == random2)
				random2 = generadorAleatorios.nextInt(this.getTamanioPoblacion());

			centromero = generadorAleatorios.nextInt(Clave.getInstancia().getTamanioClave());

			brazoCortoPadre = this.getCromosomas().get(random1).getRepresentacionBinaria().substring(0, centromero);
			brazoCortoMadre = this.getCromosomas().get(random2).getRepresentacionBinaria().substring(0, centromero);
			brazoLargoPadre = this.getCromosomas().get(random1).getRepresentacionBinaria().substring(centromero);
			brazoLargoMadre = this.getCromosomas().get(random2).getRepresentacionBinaria().substring(centromero);
			pobAux.add(new Cromosoma(Cromosoma.convertirBinAHexa(brazoCortoPadre + brazoLargoMadre)));
			pobAux.add(new Cromosoma(Cromosoma.convertirBinAHexa(brazoCortoMadre + brazoLargoPadre)));
		}

		for (Integer i = 0; i < this.getTamanioPoblacion(); i++) {
			if (this.getCromosomas().get(i).getFitness() < pobAux.get(i).getFitness())
				pobResultante.add(pobAux.get(i));
			else
				pobResultante.add(this.getCromosomas().get(i));
		}
		this.setCromosomas(pobResultante);
	}

	public void mutacion() {
		Random generadorAleatorios = new Random(System.currentTimeMillis());
		Integer cantCromosomasAMutar = generadorAleatorios.nextInt(this.getTamanioPoblacion());

		for (Integer i = 0; i < cantCromosomasAMutar; i++) {
			Integer posicion = generadorAleatorios.nextInt(this.getTamanioPoblacion());
			this.getCromosomas().get(posicion).mutar();
		}
	}

	public Integer getMayorFitnessPoblacion() {
		Integer max = 0;
		for (Integer i = 0; i < this.getTamanioPoblacion(); i++) {
			Integer fitness = this.getCromosomas().get(i).getFitness();
			if (fitness > max)
				max = fitness;
		}
		return max;
	}

	public void mostrarPoblacion() {
		StringBuilder sb = new StringBuilder();
		for (Integer i = 0; i < this.getTamanioPoblacion(); i++) {
			sb.append("Cromosoma ");
			sb.append(i);
			sb.append(": ");
			sb.append(this.getCromosomas().get(i).getRepresentacionHexadecimal());
			sb.append(" ");
			sb.append(this.getCromosomas().get(i).getRepresentacionBinaria());
			sb.append(" ");
			sb.append(this.getCromosomas().get(i).getFitness() + "\n");
		}
		System.out.println(sb.toString());
	}
}
