package eu.telecomsudparis.csc4102.pge;

import java.util.Objects;

public class Cadeau {
	private final String id;
	private String description;
	private int nbInitial;
	private int nbDisponible;
	private int cout;
	
	public Cadeau(final String id, final String description, int nbInitial, int cout) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (nbInitial < 0 ) {
			throw new IllegalArgumentException("nbInitial ne peut pas être négatif");
		
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
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Famille)) {
			return false;
		}
		Cadeau other = (Cadeau) obj;
		return id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public boolean invariant() {
		return id != null && !id.isBlank() && description != null && !description.isBlank() && nbInitial >= 0  &&  nbDisponible >= 0  && cout > 0 ;
	}
}
