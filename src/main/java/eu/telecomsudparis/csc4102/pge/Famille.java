package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

/**
 * Cette classe modélise le concept métier de famille.
 * 
 * @author Denis Conan
 */
public class Famille {
	/**
	 * l'identifiant.
	 */
	private final String id;
	/**
	 * la description, par exemple avec plusieurs nom de famille, par exemple
	 * utiliser par convention pour adresser du courrier postal à « Famille
	 * [description] ».
	 */
	private String description;
	
	private ArrayList<Enfant> enfants;

	/**
	 * construit une famille.
	 * 
	 * @param id                l'identifiant.
	 * @param description       la description.
	 */
	public Famille(final String id, final String description) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		this.id = id;
		this.description = description;
		this.enfants = new ArrayList<>();
		assert invariant();
	}

	public void ajouterUnEnfant(String idEnfant, String nom, String prenom, int nBPointsMaxParEnfant) throws OperationImpossible {
		if (chercherEnfant(idEnfant) != null) {
			throw new OperationImpossible("enfant existant avec id=" + idEnfant);
		}
		
		Enfant enfant = new Enfant(idEnfant, nom, prenom, nBPointsMaxParEnfant);
		enfants.add(enfant);
	}
	
	public Enfant chercherEnfant(String idEnfant) {
		for ( Enfant enfant : enfants) {
			if (idEnfant.equals(enfant.getId())) {
				return enfant;
			}
		}
		return null;
	}
	
	public void retirerUnEnfant(String idEnfant) throws OperationImpossible {
		if (chercherEnfant(idEnfant) == null) {
			throw new OperationImpossible("aucun enfant existant avec id=" + idEnfant);
		}
		Enfant enfant = chercherEnfant(idEnfant);
		
		enfant.retirerReservations(idEnfant);
		
		this.enfants.remove(enfant);
		
	
	}
	
	

	/**
	 * l'invariant de la classe.
	 * 
	 * @return vrai lorsqu'il est vérifié.
	 */
	public boolean invariant() {
		return id != null && !id.isBlank() && description != null && !description.isBlank();
	}

	/**
	 * obtient l'identifiant.
	 * 
	 * @return l'identifiant.
	 */
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Famille)) {
			return false;
		}
		Famille other = (Famille) obj;
		return id.equals(other.id);
	}

	@Override
	public String toString() {
		return "Famille [id=" + id + ", description=" + description + "]";
	}
	
	public void ajouterReservation(final String idEnfant, Cadeau cadeau, int quantitee ) throws OperationImpossible{
		Enfant enfant = chercherEnfant(idEnfant);
		
		if (enfant == null) {
			throw new OperationImpossible("l'enfant n'existe pas dans la famille");
		}
		
		enfant.ajouterReservation(cadeau, quantitee);
	}
	
}
