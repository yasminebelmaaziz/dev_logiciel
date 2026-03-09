package eu.telecomsudparis.csc4102.pge;

public class Enfant {
	
	private final String id;
	private String nom;
	private String prenom;
	private int nbPointsRestants;
	
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
		assert invariant();
	}

}
