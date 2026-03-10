package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.Objects;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

public class Enfant {
	
	private final String id;
	private String nom;
	private String prenom;
	private int nbPointsRestants;
	private ArrayList<Reservation> reservations;
	
	public Enfant(final String id, final String nom, final String prenom, int nbPointsRestants) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("id ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new IllegalArgumentException("nom ne peut pas être null ou vide");
		}
		if (prenom == null || prenom.isBlank()) {
			throw new IllegalArgumentException("prenom ne peut pas être null ou vide");
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
	
	public String getId() {
		return id;
	}

	public boolean invariant() {
		return id != null && !id.isBlank() && nom != null && !nom.isBlank() && prenom != null && !prenom.isBlank();
	}
	
	public void ajouterReservation(Cadeau cadeau, int quantitee ) throws OperationImpossible{
		int cout = cadeau.getCout();
		int nbDisponible = cadeau.getNbDisponible();
		
		if (nbDisponible < quantitee) {
			throw new OperationImpossible("nombre disponible insuffisant");
		}
		if (nbPointsRestants < quantitee*cout) {
			throw new OperationImpossible("nombre insuffisant de points");
		}
		
	}

	
}
