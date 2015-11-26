package ar.edu.uno.poo1.tp2.modelo;

public class Clave {
	
	private static Clave clave;
	private Cromosoma cromosoma;
	private Integer tamanioClave;
	
	//Constructor privado
	private Clave() {}
	
	public static Clave getInstancia() {
		if (clave == null)
			clave = new Clave();
		return clave;
	}
	
	public Cromosoma getCromosoma() {
		return this.cromosoma;
	}
	
	private void setCromosoma(Cromosoma x) {
		this.cromosoma = x;
	}
	
	public Integer getTamanioClave() {
		return tamanioClave;
	}
	
	private void setTamanioClave(Integer tamanio) {
		this.tamanioClave = tamanio; 
	}
	
	public void setClave(String clave) {
		this.setCromosoma(new Cromosoma(clave));
		this.setTamanioClave(clave.length());
	}
}
