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

public class TestRetirerReservation {

	private PGE facade;

	@BeforeEach
	void setUp() throws OperationImpossible {
		
		facade = new PGE(20);
		
		facade.ajouterUneFamille("f1", "Famille Dupont", new ConsommateurNotification("f1"));
		facade.ajouterUnEnfantAUneFamille("f1", "Dupont", "OuiOui", "e1");
		facade.ajouterUnCadeau("c1", "Jeu de billes", 5, 10);
		facade.ajouterUnCadeau("c2", "Sac de billes", 4, 3);
		facade.ajouterUneReservation("f1", "e1", "c1", 2);
	}

	@AfterEach
	void tearDown() {
		facade = null;
	}
	
	// test des préconditions
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("Test n°1 : erreur id famille null ou vide")
	void ajouterReservationTest1(String input) {
		Assertions.assertThrows(OperationImpossible.class, ()-> facade.retirerUneReservation(input, "e1" , "c1", 1));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2: erreur id enfant null ou vide")
	void ajouterReservationTest2(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1",  input, "c1", 1));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 3: erreur id cadeau null ou vide")
	void ajouterReservationTest3(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1", "e1", input, 1)) ;
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -2})
	@DisplayName("test 4 : erreur quantité invalide (<= 0)")
	void ajouterReservationTest4(int input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1", "e1", "c1", input));
	}



	@Test
	@DisplayName("test 5: erreur la famille n'existe pas ")
	void ajouterReservationTest5() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f_inconnue", "e1", "c1", 1));
	}

	@Test
	@DisplayName("test 6: erreur l'enfant n'existe pas")
	void ajouterReservationTest6() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1", "e_inconnu", "c1", 1));
	}

	@Test
	@DisplayName("test 7:  erreur le cadeau n'existe pas")
	void ajouterReservationTest7() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1", "e1", "c_inconnu", 1));
	}
	
	@Test
	@DisplayName("test 8:  erreur la reservation n'existe pas")
	void ajouterReservationTest8() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1", "e1", "c2", 1));
	}
	
	@Test
	@DisplayName("Test 8: erreur quantite à retirer trop importante")
	void ajouterReservationTest9() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneReservation("f1", "e1", "c1", 15));	
	}
	
	// test des 2 scénarios (soit la suppression ou la mise à jour d'une reservation)

	@Test
	@DisplayName("test 10 : succès suppression réservation ")
	void ajouterReservationTest10() throws OperationImpossible {
	
		Assertions.assertEquals(8, facade.getNbDisponibleCadeau("c1"));
		Assertions.assertEquals(10, facade.getNbPointsRestantsEnfant("f1", "e1"));

		facade.retirerUneReservation("f1", "e1", "c1", 2);

		// postconditions : nbDisponible re incrémenté, + solde de points recrédité + réservation supprimée
		Assertions.assertEquals(10, facade.getNbDisponibleCadeau("c1"));

		Assertions.assertEquals(20, facade.getNbPointsRestantsEnfant("f1", "e1"));

		Assertions.assertEquals(0, facade.listerLesReservationsDUneFamille("f1").size()) ;
	}

	@Test
	@DisplayName("test 11 : màj réservation ok")
	void ajouterReservationTest11() throws OperationImpossible {

		facade.retirerUneReservation("f1", "e1", "c1", 1);

		// postconditions aussi:
		Assertions.assertEquals(9, facade.getNbDisponibleCadeau("c1"));
		Assertions.assertEquals(15, facade.getNbPointsRestantsEnfant("f1", "e1"));
		Assertions.assertEquals(1, facade.listerLesReservationsDUneFamille("f1").size());
	}

	@Test
	@DisplayName("test 12 : notification famille envoyée quand cadeau repasse de 0 à disponible")
	void retirerReservationTest12() throws OperationImpossible, InterruptedException {
		// cadeau épuisable : coût=1, stock=3 — e1 a encore 10 points après setUp (20 - 2*5)
		facade.ajouterUnCadeau("cNotif", "cadeau notification", 1, 3);
		facade.ajouterUneReservation("f1", "e1", "cNotif", 3);
		Assertions.assertEquals(0, facade.getNbDisponibleCadeau("cNotif"));

		// f2 s'abonne aux notifications de disponibilité pour cNotif
		ConsommateurNotification consommateurF2 = new ConsommateurNotification("f2");
		facade.ajouterUneFamille("f2", "Famille Martin", consommateurF2);
		facade.demanderUneNotificationDisponibilite("f2", "cNotif");

		// retrait partiel : cNotif repasse de 0 à 1 → notification déclenchée
		facade.retirerUneReservation("f1", "e1", "cNotif", 1);
		Thread.sleep(100);

		// postcondition : f2 a reçu exactement 1 notification mentionnant cNotif
		Assertions.assertEquals(1, consommateurF2.getMessagesRecus().size());
		Assertions.assertTrue(consommateurF2.getMessagesRecus().get(0).contains("cNotif"));

		// postcondition : la demande de notification a été retirée (pas de 2e notification si cadeau repasse à 0 puis revient)
		facade.ajouterUneReservation("f1", "e1", "cNotif", 1); // remet à 0
		facade.retirerUneReservation("f1", "e1", "cNotif", 1); // repasse à 1
		Thread.sleep(100);
		Assertions.assertEquals(1, consommateurF2.getMessagesRecus().size()); // toujours 1, pas 2
	}

	@Test
	@DisplayName("test 13 : pas de notification famille si cadeau ne repasse pas par 0")
	void retirerReservationTest13() throws OperationImpossible, InterruptedException {
		// c1 a nbDisponible=8 après setUp, donc ne passe jamais par 0 lors du retrait
		ConsommateurNotification consommateurF2 = new ConsommateurNotification("f2");
		facade.ajouterUneFamille("f2", "Famille Martin", consommateurF2);
		facade.ajouterUnCadeau("cSuivi", "cadeau suivi", 1, 5);
		facade.demanderUneNotificationDisponibilite("f2", "cSuivi");

		// retrait qui ne fait pas repasser de 0 (c1 était à 8, jamais à 0)
		facade.retirerUneReservation("f1", "e1", "c1", 1);
		Thread.sleep(100);

		// aucune notification envoyée
		Assertions.assertTrue(consommateurF2.getMessagesRecus().isEmpty());
	}

}
