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
		return nBPointsMaxParEnfant >= 0 && familles != null;
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
	 * @param idFamille         l'identifiant de la famille.
	 * @param description       la description de la famille.
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
	}
	
	public void ajouterUnEnfantAUneFamille(final String idFamille, final String nom, final String prenom, final String IdEnfant) {
		
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
	 * ajoute un cadeau système.
	 * 
	 * UC1
	 */
	
	public void ajouterUnCadeau(final String idCadeau, String description, int cout, int nbInitial) throws OperationImpossible {
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
		if (cadeaux.get(idCadeau) != null) {
			throw new OperationImpossible("cadeau déjà existant avec id=" + idCadeau);
		}
		var cadeau = new Cadeau(idCadeau, description, nbInitial, cout);
		cadeaux.put(idCadeau, cadeau);	
	}
	
	
}
