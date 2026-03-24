// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.pge;

public class Reservation {
	private int quantite;
	private Cadeau cadeau;
	
	
	public Reservation(int quantite, Cadeau cadeau) {
		this.quantite = quantite;
		this.cadeau = cadeau;
		assert invariant();
	}
	
	public boolean invariant() {
		return quantite>0 ;
	}
	
	
	public Cadeau getCadeau() {
		return cadeau;
	}
	
	public int getQuantite() {
		return quantite;
	}
	
	public void incrementerQuantite(int quantite){
		this.quantite += quantite;
		assert invariant();
	}
	
	public void decrementerQuantite(int quantite){
		this.quantite -= quantite;
		assert invariant();
	}
	
	
}
