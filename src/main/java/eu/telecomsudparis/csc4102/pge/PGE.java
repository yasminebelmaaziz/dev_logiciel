package eu.telecomsudparis.csc4102.pge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

/**
 * Cette classe est le point d'entrée du système, c'est-à-dire la façade.
 * 
 * @author Denis Conan
 */
public class PGE {
	/**
	 * le nombre de points maximum par enfant. C'est le même pour tous et c'est un
	 * paramètre de configuration de l'application.
	 */
	private static int nBPointsMaxParEnfant;
	/**
	 * la collection des familles. Les enfants sont organisés par famille.
	 */
	private Map<String, Famille> familles;
	/**
	 * la collection des cadeaux.
	 */
	private Map<String, Cadeau> cadeaux;

	/**
	 * construit la facade.
	 * 
	 * @param nbPtsMaxParEnfant le nombre de points maximum par enfant.
	 */
	public PGE(final int nbPtsMaxParEnfant) {
		if (nbPtsMaxParEnfant <= 0) {
			throw new IllegalArgumentException("nombre points max par enfant ne peut pas être négatif ou 0");
		}
		setNBPointsMaxParEnfant(nbPtsMaxParEnfant);
		familles = new HashMap<>();
		cadeaux = new HashMap<>();
	}

	/**
	 * l'invariant de la classe.
	 * 
	 * @return vrai lorsqu'il est vérifié.
	 */
	public boolean invariant() {
		return nBPointsMaxParEnfant >= 0 && familles != null && cadeaux != null;
	}

	/**
	 * obtient le nombre de points maximum par enfant.
	 * 
	 * @return la valeur entière.
	 */
	public static int getNBPointsMaxParEnfant() {
		return nBPointsMaxParEnfant;
	}

	/**
	 * fixe le nombre de points maximum par enfant.
	 * 
	 * @param nbPtsMaxParEnfant la nouvelle valeur.
	 */
	private static void setNBPointsMaxParEnfant(final int nbPtsMaxParEnfant) {
		nBPointsMaxParEnfant = nbPtsMaxParEnfant;
	}

	/**
	 * ajoute une famille.
	 * 
	 * @param idFamille   l'identifiant de la famille.
	 * @param description la description de la famille.
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */
	public void ajouterUneFamille(final String idFamille, final String description) throws OperationImpossible {
		if (idFamille == null || idFamille.isBlank()) {
			throw new OperationImpossible("id famille ne peut pas être null ou vide");
		}
		if (description == null || description.isBlank()) {
			throw new OperationImpossible("description ne peut pas être null ou vide");
		}
		if (familles.get(idFamille) != null) {
			throw new OperationImpossible("famille déjà existante avec id=" + idFamille);
		}
		var famille = new Famille(idFamille, description);
		familles.put(idFamille, famille);
		assert invariant();
	}

	/**
	 * ajoute un enfant à une famille.
	 * 
	 * @param idFamille l'identifiant de la famille.
	 * @param nom       nom de l'enfant
	 * @param prenom    prénom de l'enfant
	 * @param idEnfant  l'identifiant de l'enfant
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */
	public void ajouterUnEnfantAUneFamille(final String idFamille, final String nom, final String prenom,
			final String idEnfant) throws OperationImpossible {
		if (idFamille == null || idFamille.isBlank()) {
			throw new OperationImpossible("id famille ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		if (prenom == null || prenom.isBlank()) {
			throw new OperationImpossible("prenom ne peut pas être null ou vide");
		}
		if (idEnfant == null || idEnfant.isBlank()) {
			throw new OperationImpossible("id enfant ne peut pas être null ou vide");
		}
		if (familles.get(idFamille) == null) {
			throw new OperationImpossible("famille n'existe pas avec id=" + idFamille);
		}

		Famille famille = familles.get(idFamille);

		int nBPoints = PGE.nBPointsMaxParEnfant;

		famille.ajouterUnEnfant(idEnfant, nom, prenom, nBPoints);

		assert invariant();
	}

	/**
	 * liste les familles du système.
	 * 
	 * @return une collection de chaînes de caractères, une par famille.
	 */
	public List<String> listerLesFamilles() {
		return familles.values().stream().map(Famille::toString).toList();
	}

	/**
	 * liste les cadeaux du système.
	 * 
	 * @return une collection de chaînes de caractères, une par cadeau.
	 */
	public List<String> listerLesCadeaux() {
		return cadeaux.values().stream().map(Cadeau::toString).toList();
	}

	@Override
	public String toString() {
		return "PGE [familles=" + familles + ", cadeaux=" + cadeaux + "]";
	}

	/**
	 * ajoute un cadeau système.
	 * 
	 * @param idCadeau    l'identifiant du cadeau.
	 * @param description description du cadeau.
	 * @param cout        coût en points du cadeau.
	 * @param nbInitial   nombre initial du cadeau.
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */

	public void ajouterUnCadeau(final String idCadeau, final String description, final int cout, final int nbInitial)
			throws OperationImpossible {
		if (idCadeau == null || idCadeau.isBlank()) {
			throw new OperationImpossible("idCadeau ne peut pas être null ou vide");
		}
		if (cadeaux.get(idCadeau) != null) {
			throw new OperationImpossible("cadeau déjà existant avec id=" + idCadeau);
		}
		if (description == null || description.isBlank()) {
			throw new OperationImpossible("description ne peut pas être null ou vide");
		}
		if (cout <= 0) {
			throw new OperationImpossible("le coût du cadeau ne peut pas être négatif ou nul");
		}
		if (nbInitial < 0) {
			throw new OperationImpossible("le coût du cadeau ne peut pas être négatif");
		}
		var cadeau = new Cadeau(idCadeau, description, nbInitial, cout);
		cadeaux.put(idCadeau, cadeau);

		assert invariant();
	}

	/**
	 * ajoute une réservation pour un enfant d'une famille au système.
	 * 
	 * @param idFamille l'identifiant de la famille.
	 * @param idEnfant  l'identifiant de l'enfant.
	 * @param idCadeau  l'identifiant du cadeau.
	 * @param quantite  quantité voulue du cadeau
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */

	public void ajouterUneReservation(final String idFamille, final String idEnfant, final String idCadeau,
			final int quantite) throws OperationImpossible {
		if (idFamille == null || idFamille.isBlank()) {
			throw new OperationImpossible("idFamille ne peut pas être null ou vide");
		}

		if (idEnfant == null || idEnfant.isBlank()) {
			throw new OperationImpossible("idEnfant ne peut pas être null ou vide");
		}

		if (idCadeau == null || idCadeau.isBlank()) {
			throw new OperationImpossible("idCadeau ne peut pas être null ou vide");
		}

		if (quantite <= 0) {
			throw new OperationImpossible("la quantité doit être strictement positive");
		}

		Famille famille = familles.get(idFamille);
		if (famille == null) {
			throw new OperationImpossible("la famille n'existe pas avec id=" + idFamille);
		}

		Cadeau cadeau = cadeaux.get(idCadeau);
		if (cadeau == null) {
			throw new OperationImpossible("le cadeau n'existe pas avec id=" + idCadeau);
		}

		famille.ajouterReservation(idEnfant, cadeau, quantite);

		assert invariant();
	}

	/**
	 * retire un enfant d'une famille.
	 * 
	 * @param idFamille l'identifiant de la famille.
	 * @param idEnfant  l'identifiant de l'enfant.
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */

	public void retirerUnEnfant(final String idFamille, final String idEnfant) throws OperationImpossible {
		if (idFamille == null || idFamille.isBlank()) {
			throw new OperationImpossible("idFamille ne peut pas être null ou vide");
		}

		if (idEnfant == null || idEnfant.isBlank()) {
			throw new OperationImpossible("idEnfant ne peut pas être null ou vide");
		}

		Famille famille = familles.get(idFamille);
		if (famille == null) {
			throw new OperationImpossible("la famille n'existe pas avec id=" + idFamille);
		}

		famille.retirerUnEnfant(idEnfant);

	}

	/**
	 * retire une famille du système.
	 * 
	 * @param idFamille l'identifiant de la famille.
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */

	public void retirerUneFamille(final String idFamille) throws OperationImpossible {
		if (idFamille == null || idFamille.isBlank()) {
			throw new OperationImpossible("idFamille ne peut pas être null ou vide");
		}

		Famille famille = familles.get(idFamille);
		if (famille == null) {
			throw new OperationImpossible("la famille n'existe pas avec id=" + idFamille);
		}

		famille.nettoyageAvantSupr();

		familles.remove(idFamille);

	}
	
	/**
	 * retire un cadeau du système.
	 * 
	 * @param idCadeau l'identifiant du cadeau.
	 * @throws OperationImpossible problème détecté par la logique métier.
	 */
	
	public void retirerUnCadeau(final String idCadeau) throws OperationImpossible {
		if (idCadeau == null || idCadeau.isBlank()) {
			throw new OperationImpossible("idCadeau ne peut pas être null ou vide");
		}

		Cadeau cadeau = cadeaux.get(idCadeau);
		if (cadeau == null) {
			throw new OperationImpossible("le cadeau n'existe pas avec id=" + idCadeau);
		}

		if (cadeau.verifierReservation()) {
			cadeaux.remove(idCadeau);
		} else {
			throw new OperationImpossible("ce cadeau ne peut pas être retiré car il est déjà réservé");
		}


	}
	
	public void retirerUneReservation(final String idFamille, final String idEnfant, final String idCadeau,
			final int quantite) throws OperationImpossible {
		if (idFamille == null || idFamille.isBlank()) {
			throw new OperationImpossible("idFamille ne peut pas être null ou vide");
		}

		if (idEnfant == null || idEnfant.isBlank()) {
			throw new OperationImpossible("idEnfant ne peut pas être null ou vide");
		}

		if (idCadeau == null || idCadeau.isBlank()) {
			throw new OperationImpossible("idCadeau ne peut pas être null ou vide");
		}

		if (quantite <= 0) {
			throw new OperationImpossible("la quantité doit être strictement positive");
		}

		Famille famille = familles.get(idFamille);
		if (famille == null) {
			throw new OperationImpossible("la famille n'existe pas avec id=" + idFamille);
		}

		Cadeau cadeau = cadeaux.get(idCadeau);
		if (cadeau == null) {
			throw new OperationImpossible("le cadeau n'existe pas avec id=" + idCadeau);
		}

		famille.retirerReservation(idEnfant, cadeau, quantite);

		assert invariant();
	}
	
}
