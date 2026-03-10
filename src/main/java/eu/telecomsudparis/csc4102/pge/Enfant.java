package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.Objects;

public class Enfant {
	
	private final String id;
	private String nom;
	private String prenom;
	private int nbPointsRestants;
	private ArrayList<Reservation> reservations;
	
	public Enfant(final String id, final String nom, final String prenom, int nbPointsRestants) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.nbPointsRestants = nbPointsRestants;
		this.reservations = new ArrayList<>();

	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public boolean invariant() {
		return id != null && !id.isBlank() && nom != null && !nom.isBlank() && prenom != null && !prenom.isBlank() && nbPointsRestants>=0;
	}
	
}
