// CHECKSTYLE:OFF 
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

	public String getId() {
		return id;
	}

	public boolean invariant() {
		return id != null && !id.isBlank() && nom != null && !nom.isBlank() && prenom != null && !prenom.isBlank();
	}

	public void ajouterReservation(Cadeau cadeau, int quantite) throws OperationImpossible {
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
	
	public void retirerReservation(Cadeau cadeau, int quantite) throws OperationImpossible {
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

	private Reservation chercherReservation(Cadeau cadeau) {
		for (Reservation res : reservations) {
			if (res.getCadeau().equals(cadeau)) {
				return res;
			}
		}
		return null;
	}

	private void decrementerPoints(int points) {
		this.nbPointsRestants -= points;
		assert invariant();
	}

	private void incrementerPoints(int points) {
		this.nbPointsRestants += points;
		assert invariant();
	}


	public List<String> listerLesReservations() {
		return reservations.stream().map(Reservation::toString).toList();
	}

	public void retirerReservations(String idEnfant) {

		for (Reservation res : reservations) {
			int quantite = res.getQuantite();
			Cadeau cadeau = res.getCadeau();

			cadeau.incrementerNbDisponible(quantite);

		}
		this.reservations.clear();
	}

}
