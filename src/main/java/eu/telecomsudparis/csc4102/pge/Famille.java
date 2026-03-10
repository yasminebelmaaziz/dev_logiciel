package eu.telecomsudparis.csc4102.pge;

import java.util.List;
import java.util.Objects;

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
	
	private List enfants;

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
		assert invariant();
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
	
	public void ajouterReservation() {}
	
}
