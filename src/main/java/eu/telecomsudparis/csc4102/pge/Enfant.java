package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

public class Enfant {

	@Override
	public String toString() {
		return "Enfant [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", nbPointsRestants=" + nbPointsRestants
				+ ", reservations=" + reservations + "]";
	}

	/**
	 * l'identifiant de l'enfant.
	 */
	private final String id;
	/**
	 * nom de l'enfant.
	 */
	private String nom;
	/**
	 * prenom.
	 */
	private String prenom;
	/**
	 * nombre de points restants.
	 */
	private int nbPointsRestants;
	/**
	 * reservations de l'enfant.
	 */
	private ArrayList<Reservation> reservations;

	/**
	 * constructeur un enfant.
	 * 
	 * @param id				l'identifiant de l'enfant.
	 * @param nom				nom.
	 * @param prenom			prenom.
	 * @param nbPointsRestants	nombre de points restants.
	 */
	public Enfant(final String id, final String nom, final String prenom, final int nbPointsRestants) {
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
		if (!(obj instanceof Enfant)) {
			return false;
		}
		Enfant other = (Enfant) obj;
		return id.equals(other.id);
	}

	/**
	 * getter identifiant.
	 * 
	 * @return	renvoie l'identifiant.
	 */
	public String getId() {
		return id;
	}

	/**
	 * recupère le nombre de points restants de l'enfant.
	 * @return	renvoie le nombre de points restants de l'enfant.
	 */
	public int getNbPointsRestants() {
		return nbPointsRestants;
	}

	/**
	 * invariant de la class.
	 * 
	 * @return	vrai lorsqu'il est vérifié.
	 */
	public boolean invariant() {
		return id != null && !id.isBlank() && nom != null && !nom.isBlank() && prenom != null && !prenom.isBlank();
	}

	/**
	 * ajoute une reservation.
	 * 
	 * @param cadeau				le cadeau.
	 * @param quantite				la quantite.
	 * @throws OperationImpossible	le problème détecté par la logique métier.
	 */
	public void ajouterReservation(final Cadeau cadeau, final int quantite) throws OperationImpossible {
		int cout = cadeau.getCout();
		int nbDisponible = cadeau.getNbDisponible();

		if (nbDisponible < quantite) {
			throw new OperationImpossible("nombre disponible insuffisant");
		}
		if (nbPointsRestants < quantite * cout) {
			throw new OperationImpossible("nombre insuffisant de points");
		}

		Reservation res = chercherReservation(cadeau);

		if (res != null) {
			res.incrementerQuantite(quantite);
		} else {
			res = new Reservation(quantite, cadeau);
			this.reservations.add(res);
		}

		decrementerPoints(quantite * cout);
		cadeau.decrementerNbDisponible(quantite);

		assert invariant();
	}
	
	/**
	 * retire quantite de cadeau de la reservation de l'enfant.
	 * 
	 * @param cadeau				le cadeau.
	 * @param quantite				la quantite.
	 * @throws OperationImpossible	le problème détecté par la logique métier.
	 */
	public void retirerReservation(final Cadeau cadeau, final int quantite) throws OperationImpossible {
		int cout = cadeau.getCout();
		Reservation res = chercherReservation(cadeau);
		
		if (res == null) {
			throw new OperationImpossible("l'enfant n'a pas de reservation pour ce cadeau");
		}
		
		int quantiteDejaReservee = res.getQuantite();
		
		if (quantiteDejaReservee == quantite) {
			incrementerPoints(quantite * cout);
			cadeau.incrementerNbDisponible(quantite);
			this.reservations.remove(res);
		}
		if (quantiteDejaReservee > quantite) {
			incrementerPoints(quantite * cout);
			cadeau.incrementerNbDisponible(quantite);
			res.decrementerQuantite(quantite);
		}
		if (quantiteDejaReservee < quantite) {
			throw new OperationImpossible("la quantite retirer doit être inférieur ou égale à la quantite déjà réservée");
		}

		assert invariant();
	}

	/**
	 * cherche la reservation associé au cadeau considéré.
	 * 
	 * @param cadeau	le cadeau.
	 * @return			renvoie la reservation du cadeau.
	 */
	private Reservation chercherReservation(final Cadeau cadeau) {
		for (Reservation res : reservations) {
			if (res.getCadeau().equals(cadeau)) {
				return res;
			}
		}
		return null;
	}

	/**
	 * decremente le nombre de points de l'enfant.
	 * 
	 * @param points	nombre de points.
	 */
	private void decrementerPoints(final int points) {
		this.nbPointsRestants -= points;
		assert invariant();
	}

	/**
	 * incremente le nombre de points de l'enfant.
	 * 
	 * @param points	nombre de points.
	 */
	private void incrementerPoints(final int points) {
		this.nbPointsRestants += points;
		assert invariant();
	}


	/**
	 * liste les reservations.
	 * 
	 * @return renvoie les reservations.
	 */
	public List<String> listerLesReservations() {
		return reservations.stream().map(Reservation::toString).toList();
	}

	/**
	 * retire toutes les reservations.
	 * 
	 * @param idEnfant	l'identifiant de l'enfant.
	 */
	public void retirerReservations(final String idEnfant) {

		for (Reservation res : reservations) {
			int quantite = res.getQuantite();
			Cadeau cadeau = res.getCadeau();

			cadeau.incrementerNbDisponible(quantite);

		}
		this.reservations.clear();
	}

}
