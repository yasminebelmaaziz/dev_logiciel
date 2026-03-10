package eu.telecomsudparis.csc4102.pge;

import java.util.Objects;

public class Cadeau {
	private final String id;
	private String description;
	private int nbInitial;
	private int nbDisponible;
	private int cout;
	
	public Cadeau(final String id, final String description, int nbInitial, int nbDisponible, int cout) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (nbInitial < 0 ) {
			throw new IllegalArgumentException("nbInitial ne peut pas être négatif");
		}
		if (nbDisponible < 0) {
			throw new IllegalArgumentException("nbDisponible ne peut pas être négatif");
		}
		if (cout <= 0) {
			throw new IllegalArgumentException("cout ne peut pas être négatif ou nul");
		}
		
		this.id = id;
		this.description = description;
		this.nbInitial = nbInitial;
		this.nbDisponible = nbInitial;
		this.cout = cout;
		
	}
}
