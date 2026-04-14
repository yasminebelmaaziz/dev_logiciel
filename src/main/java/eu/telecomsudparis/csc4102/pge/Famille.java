// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.SubmissionPublisher;

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

	/**
	 * liste d'enfants.
	 * Nous faisons le choix d'utiliser une ArrayList plutôt qu'une HashMap car on suppose qu'il n'y pas beaucoup d'enfants
	 */
	private ArrayList<Enfant> enfants;
	/**
	 * producteur.
	 */
	private SubmissionPublisher<String> producteur;
	/**
	 * liste des cadeaux suivis.
	 */
	private List<String> cadeauxSuivis;

	/**
	 * construit une famille.
	 * 
	 * @param id          l'identifiant.
	 * @param description la description.
	 * @param consommateur le consommateur.
	 */
	public Famille(final String id, final String description, final ConsommateurNotification consommateur) {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("id ne peut pas être null ou vide");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("description ne peut pas être null ou vide");
		}
		if (consommateur == null) {
			throw new IllegalArgumentException("consommateur ne peut pas être null");
		}
		
		this.id = id;
		this.description = description;
		this.enfants = new ArrayList<>();
		this.producteur = new SubmissionPublisher<>();
		this.cadeauxSuivis = new ArrayList<>();
		producteur.subscribe(consommateur);
		assert invariant();
	}

	/**
	 * @param idEnfant					l'identifiant de l'enfant.
	 * @param nom						le nom de l'enfant.
	 * @param prenom					le prenom de l'enfant.
	 * @param nBPointsMaxParEnfant		le nombre de points maximum de chaque enfant.
	 * @throws OperationImpossible		le problème détecté par la logique métier.
	 */
	public void ajouterUnEnfant(final String idEnfant, final String nom, final String prenom, final int nBPointsMaxParEnfant)
			throws OperationImpossible {
		if (chercherEnfant(idEnfant) != null) {
			throw new OperationImpossible("enfant existant avec id=" + idEnfant);
		}

		Enfant enfant = new Enfant(idEnfant, nom, prenom, nBPointsMaxParEnfant);
		enfants.add(enfant);
	}

	/**
	 * @param idEnfant	l'identifiant de l'enfant.
	 * @return			renvoie l'enfant avec l'identifiant idEnfant.
	 */
	public Enfant chercherEnfant(final String idEnfant) {
		for (Enfant enfant : enfants) {
			if (idEnfant.equals(enfant.getId())) {
				return enfant;
			}
		}
		return null;
	}

	/**
	 * @param idEnfant					l'identfifiant de l'enfant.
	 * @throws OperationImpossible		le problème détecté par la logique métier.
	 */
	public void retirerUnEnfant(final String idEnfant) throws OperationImpossible {
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

	/**
	 * @param idEnfant				l'identifiant enfant.
	 * @param cadeau				le cadeau.
	 * @param quantitee				la quantitee de cadeaux qu'on souhaite réserver.
	 * @throws OperationImpossible	le problème détecté par la logique métier.
	 */
	public void ajouterReservation(final String idEnfant, final Cadeau cadeau, final int quantitee) throws OperationImpossible {
		Enfant enfant = chercherEnfant(idEnfant);

		if (enfant == null) {
			throw new OperationImpossible("l'enfant n'existe pas dans la famille");
		}

		enfant.ajouterReservation(cadeau, quantitee);
	}
	
	
	public void retirerReservation(final String idEnfant, Cadeau cadeau, int quantitee) throws OperationImpossible {
		Enfant enfant = chercherEnfant(idEnfant);

		if (enfant == null) {
			throw new OperationImpossible("l'enfant n'existe pas dans la famille");
		}

		enfant.retirerReservation(cadeau, quantitee);
	}

	public void nettoyageAvantSupr() {
		for (Enfant enfant : enfants) {
			String idEnfant = enfant.getId();
			enfant.retirerReservations(idEnfant);
		}

		this.enfants.clear();
	}
	
	

	public List<String> listerLesEnfants() {
		return enfants.stream().map(Enfant::toString).toList();
	}

	public int getNbPointsRestantsEnfant(final String idEnfant) throws OperationImpossible {
		Enfant enfant = chercherEnfant(idEnfant);
		if (enfant == null) {
			throw new OperationImpossible("enfant non trouvé avec id=" + idEnfant);
		}
		return enfant.getNbPointsRestants();
	}


	public List<String> listerLesReservations() {
		return enfants.stream()
				.flatMap(e -> e.listerLesReservations().stream())
				.toList();
	}

	public void ajouterCadeauSuivi(final String idCadeau) {
		if (!cadeauxSuivis.contains(idCadeau)) {
			cadeauxSuivis.add(idCadeau);
		}
	}

	public void retirerCadeauSuivi(final String idCadeau) {
		cadeauxSuivis.remove(idCadeau);
	}

	public boolean suitCeCadeau(final String idCadeau) {
		return cadeauxSuivis.contains(idCadeau);
	}

	public void notifierDisponibilite(final Cadeau cadeau) {
		producteur.submit("Cadeau disponible : " + cadeau.toString());
	}

}
