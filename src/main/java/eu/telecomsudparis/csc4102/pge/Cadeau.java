package eu.telecomsudparis.csc4102.pge;

import java.util.Objects;

/**
 * Cette classe modélise le concept métier de Cadeau.
 */
public class Cadeau {
	/**
	 * l'identifiant.
	 */
	private final String id;
	/**
	 * la description, c'est la dénomitation du cadeau considéré.
	 */
	private String description;
	/**
	 * le nombre de cadeau initialement disponible.
	 */
	private int nbInitial;
	/**
	 * le nombre de cadeau actuellement disponible.
	 */
	private int nbDisponible;
	/**
	 * le nombre de point que coût une unité du cadeau considéré.
	 */
	private int cout;

	/**
	 * construit un cadeau.
	 * 
	 * @param id
	 * @param description
	 * @param nbInitial
	 * @param cout
	 */
	public Cadeau(final String id, final String description, final int nbInitial, final int cout) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("id ne peut pas être null ou vide");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (nbInitial < 0) {
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
		assert invariant();

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
		if (!(obj instanceof Cadeau)) {
			return false;
		}
		Cadeau other = (Cadeau) obj;
		return id.equals(other.id);
	}

	/**
	 * l'invariant de la classe.
	 * 
	 * @return vrai lorsqu'il est vérifié.
	 */
	public boolean invariant() {
		return id != null 
				&& !id.isBlank() 
				&& description != null
				&& !description.isBlank() 
				&& nbInitial >= 0
				&& nbDisponible >= 0
				&& cout > 0
				&& nbDisponible <= nbInitial;
	}

	/**
	 * obtient le nombre de cadeau disponible.
	 * 
	 * @return le nombre de cadeau disponible.
	 */
	public int getNbDisponible() {
		return nbDisponible;
	}

	/**
	 * obtient le coût.
	 * 
	 * @return le coût.
	 */
	public int getCout() {
		return cout;
	}

	/**
	 * décrémente le nombre de cadeau disponible de "[quantite] cadeaux".
	 * 
	 * @param quantite
	 */
	public void decrementerNbDisponible(final int quantite) {
		if (quantite <= 0) {
			throw new IllegalArgumentException("la quantité à réserver doit être strictement positive");
		}
		if (quantite > this.nbDisponible) {
			throw new IllegalArgumentException("quantité réservable insuffisante");
		}
		this.nbDisponible -= quantite;
		assert invariant();
	}

	/**
	 * incrémente le nombre de cadeau disponible de "[quantite] cadeaux".
	 * 
	 * @param quantite
	 */
	public void incrementerNbDisponible(final int quantite) {
		if (quantite <= 0) {
			throw new IllegalArgumentException("la quantité à ajouter doit être strictement positive");
		}
		this.nbDisponible += quantite;
		assert invariant();
	}
	
	/**
	 * incrémente le nombre initial de cadeau de "[quantite] cadeaux" (utile pour le réassort)
	 * 
	 * @param quantite
	 */
	public void incrementerNbInitial(final int quantite) {
		if (quantite <= 0) {
			throw new IllegalArgumentException("la quantité à ajouter doit être strictement positive");
		}
		this.nbInitial += quantite;
		assert invariant();
	}

	@Override
	public String toString() {
		return "Cadeau [id=" + id + ", description=" + description + ", nbInitial=" + nbInitial + ", nbDisponible="
				+ nbDisponible + ", cout=" + cout + "]";
	}
	
	/**
	 * vérifie si une réservation est en cours pour ce cadeau.
	 * 
	 * @return true si nbInitial = nbDisponible
	 */
	
	public boolean verifierReservation() {
	    return (this.nbInitial == this.nbDisponible);
	}
}
