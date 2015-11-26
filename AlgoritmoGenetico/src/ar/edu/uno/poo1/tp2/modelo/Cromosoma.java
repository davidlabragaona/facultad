package ar.edu.uno.poo1.tp2.modelo;

import java.util.Random;

public class Cromosoma {

	Long genes;

	public Cromosoma(String hexadecimal) {
		if (!hexadecimal.isEmpty()) {
			this.setGenes(this.convertirHexaALong(hexadecimal));
		}
	}

	private Long getGenes() {
		return genes;
	}

	private void setGenes(Long genes) {
		this.genes = genes;
	}

	private Long convertirHexaALong(String hexa) {
		return Long.parseLong(hexa, 16);
	}
	
	public static String convertirBinAHexa(String bin) {
		Long valor = Long.parseLong(bin, 2);
		return Long.toHexString(valor);
	}

	public String getRepresentacionHexadecimal() {
		Integer tamanio = Clave.getInstancia().getTamanioClave();
		String formato = "%" + tamanio + "s";
		return String.format(formato, Long.toHexString(this.getGenes()).toUpperCase()).replace(' ', '0');
	}

	public String getRepresentacionBinaria() {
		Integer tamanio = 0;
		String temp = Long.toBinaryString(this.getGenes()) ;
		
		//Como la función toBinaryString no siempre devuelve los valores necesarios
		//controlamos el tamaño
		tamanio = Clave.getInstancia().getTamanioClave() * 4; 
		
		String formato = "%" + tamanio + "s";
		return String.format(formato, temp).replace(' ', '0');
	}
	
	public Integer getFitness() {
		Integer h = this.compararClaveHexadecimal(Clave.getInstancia().getCromosoma().getRepresentacionHexadecimal());
		Integer b = this.compararClaveBinaria(Clave.getInstancia().getCromosoma().getRepresentacionBinaria());
		return (int) ((h + 1) * Math.pow(b, 2));
	}
	
	public Integer compararClaveHexadecimal(String clave) {
		Integer contador = 0;
		for (Integer i = 0; i < clave.length(); i++)
			if (this.getRepresentacionHexadecimal().toCharArray()[i] == clave.toCharArray()[i])
				contador++;
		return contador;
	}
	
	public Integer compararClaveBinaria(String clave) {
		Integer contador = 0;
		for (Integer i = 0; i < clave.length(); i++)
			if (this.getRepresentacionBinaria().toCharArray()[i] == clave.toCharArray()[i])
				contador++;
		return contador;
	}
	
	public void mutar() {
		Random generadorAleatorio = new Random(System.currentTimeMillis());
		Integer cantGenesAMutar = generadorAleatorio.nextInt(Clave.getInstancia().getTamanioClave() * 4);
		char genesMutados[] = this.getRepresentacionBinaria().toCharArray(); 
		for (Integer i = 0; i < cantGenesAMutar; i++) {
			Integer posicion = generadorAleatorio.nextInt(Clave.getInstancia().getTamanioClave() * 4);
			genesMutados[posicion] = (genesMutados[posicion] == '1') ? '0' : '1';
		}
		this.setGenes(this.convertirHexaALong(Cromosoma.convertirBinAHexa(new String(genesMutados))));
	}
}
