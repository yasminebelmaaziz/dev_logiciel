package eu.telecomsudparis.csc4102.pge;

public class Reservation {
	@Override
	public String toString() {
		return "Reservation [quantite=" + quantite + ", cadeau=" + cadeau + "]";
	}

	/**
	 * quantite de cadeau.
	 */
	private int quantite;
	/**
	 * cadeau.
	 */
	private Cadeau cadeau;

	/**
	 * @param quantite	la quantité de cadeaux réservés.
	 * @param cadeau	le cadeau réservé.
	 */
	public Reservation(final int quantite, final Cadeau cadeau) {
		this.quantite = quantite;
		this.cadeau = cadeau;
		assert invariant();
	}

	/**
	 * l'invariant de la classe.
	 * 
	 * @return	vrai lorsqu'il est vérifié.
	 */
	public boolean invariant() {
		return quantite > 0;
	}

	/**
	 * getter de cadeau.
	 * 
	 * @return	renvoie le cadeau.
	 */
	public Cadeau getCadeau() {
		return cadeau;
	}

	/**
	 * getter de quantite.
	 * 
	 * @return	renvoie la quantite.
	 */
	public int getQuantite() {
		return quantite;
	}

	/**
	 * incremente de quantite la reservation.
	 * 
	 * @param quantite	la quantite.
	 */
	public void incrementerQuantite(final int quantite) {
		this.quantite += quantite;
		assert invariant();
	}

	/**
	 * decremente de quantite la reservation.
	 * @param quantite
	 */
	public void decrementerQuantite(final int quantite) {
		this.quantite -= quantite;
		assert invariant();
	}

}
