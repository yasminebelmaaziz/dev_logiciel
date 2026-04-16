// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.pge.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import eu.telecomsudparis.csc4102.pge.ConsommateurNotification;
import eu.telecomsudparis.csc4102.pge.PGE;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

public class TestReassortir {

	private PGE facade;
	private ConsommateurNotification consommateurFAM;

	@BeforeEach
	void setUp() throws OperationImpossible {
		facade = new PGE(10000);
		consommateurFAM = new ConsommateurNotification("FAM");
		facade.ajouterUneFamille("FAM", "famille test", consommateurFAM);
		facade.ajouterUnEnfantAUneFamille("FAM", "nom", "prenom", "enf1");

		facade.ajouterUnCadeau("c1", "Jeu de billes", 5, 10);
		facade.ajouterUnCadeau("c2", "Jeu de billes", 5, 10);
		facade.ajouterUneReservation("FAM", "enf1", "c1", 1);
	}

	@AfterEach
	void tearDown() {
		facade = null;
		consommateurFAM = null;
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 1: id cadeau vide ou null")
	void TestReassortir1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.reassortir(input, 2));
	}

	@Test
	@DisplayName("test 2: erreur le cadeau n'existe pas")
	void TestReassortir2() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.reassortir("acdeauuuuu", 2));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -2})
	@DisplayName("test 3 : erreur quantité invalide (<= 0)")
	void TestReassortir3(int input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.reassortir("c1", input));
	}

	@Test
	@DisplayName(" test 4 : nbDisponible incrémenté, nbInitial mis à jour (cadeau non épuisé)")
	void TestReassortir4() throws OperationImpossible {

		facade.reassortir("c1", 1);

		//postconditions =
		//nbDispo doit être ré incrémenté
		Assertions.assertEquals(10, facade.getNbDisponibleCadeau("c1"));
		Assertions.assertEquals(11, facade.getNbInitialCadeau("c1")); // nbInitial mis à jour aussi
	}

	@Test
	@DisplayName("test 5: notification famille envoyée quand cadeau épuisé redevient disponible via réassort")
	void TestReassortir5() throws OperationImpossible, InterruptedException {
		// on épuise c2
		facade.ajouterUnEnfantAUneFamille("FAM", "nom2", "prenom2", "enf2");
		facade.ajouterUneReservation("FAM", "enf2", "c2", 10);
		Assertions.assertEquals(0, facade.getNbDisponibleCadeau("c2"));

		//une2e famille s'abonne à la notif de disponibilite pour c2
		ConsommateurNotification consommateurFAM2 = new ConsommateurNotification("FAM2");
		facade.ajouterUneFamille("FAM2", "Famille Martin", consommateurFAM2) ;
		facade.demanderUneNotificationDisponibilite("FAM2" , "c2");

		
		facade.reassortir("c2", 3);
		Thread.sleep(100);

		// postconditions : FAM2 a bien reçu une notification mentionnant c2
		Assertions.assertEquals(1, consommateurFAM2.getMessagesRecus().size());
		Assertions.assertTrue(consommateurFAM2.getMessagesRecus().get(0).contains("c2"));
	}

	@Test
	@DisplayName("test 6: pas de notif si le cadeau était pas épuisé avant le réassort")
	void TestReassortir6() throws OperationImpossible, InterruptedException {
		
		ConsommateurNotification consommateurFAM2 = new ConsommateurNotification("FAM2");
		facade.ajouterUneFamille("FAM2", "Famille Martin", consommateurFAM2);

		facade.reassortir("c1", 2);
		Thread.sleep(100);

		Assertions.assertEquals(0, consommateurFAM2.getMessagesRecus().size());
	}

}
