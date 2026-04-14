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

class TestAjouterReservation {

	private PGE facade;

	@BeforeEach
	void setUp() throws OperationImpossible {
		
		facade = new PGE(20);
		
		facade.ajouterUneFamille("f1", "Famille Dupont", new ConsommateurNotification("f1"));
		facade.ajouterUnEnfantAUneFamille("f1", "Dupont", "OuiOui", "e1");
		
		facade.ajouterUnCadeau("c1", "Jeu de billes", 5, 10);
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
		Assertions.assertThrows(OperationImpossible.class, ()-> facade.ajouterUneReservation(input, "e1" , "c1", 1));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2: erreur id enfant null ou vide")
	void ajouterReservationTest2(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1",  input, "c1", 1));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 3: erreur id cadeau null ou vide")
	void ajouterReservationTest3(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1", "e1", input, 1)) ;
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -2})
	@DisplayName("test 4 : erreur quantité invalide (<= 0)")
	void ajouterReservationTest4(int input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1", "e1", "c1", input));
	}



	@Test
	@DisplayName("test 5: erreur la famille n'existe pas ")
	void ajouterReservationTest5() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f_inconnue", "e1", "c1", 1));
	}

	@Test
	@DisplayName("test 6: erreur l'enfant n'existe pas")
	void ajouterReservationTest6() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1", "e_inconnu", "c1", 1));
	}

	@Test
	@DisplayName("test 7:  erreur le cadeau n'existe pas")
	void ajouterReservationTest7() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1", "e1", "c_inconnu", 1));
	}


	@Test
	@DisplayName("Test 8: erreur stock insuffisant")
	void ajouterReservationTest8() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1", "e1", "c1", 15));
		
	}

	@Test
	@DisplayName("test 9: erreur solde de points insuffisant ")
	void ajouterReservationTest9() {
		
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneReservation("f1", "e1", "c1", 5));
	}

// test des 2 scénarios (soit la création ou la mise à j d'une reservation

	@Test
	@DisplayName("test 10 : succès création d'une nouvelle réservation")
	void ajouterReservationTest10() throws OperationImpossible {
		
		facade.ajouterUneReservation("f1", "e1", "c1", 1);
		
		//nbDisponible et solde points bien décrémentés
		Assertions.assertEquals(9, facade.getNbDisponibleCadeau("c1"));
		Assertions.assertEquals(15, facade.getNbPointsRestantsEnfant("f1", "e1"));
		
		// on a bien une seule réservation (donc on a une maj, pas création)
		Assertions.assertEquals(1, facade.listerLesReservationsDUneFamille("f1").size());
	}
	
	

	@Test
	@DisplayName("test 11 : succès mise à jour d'une réservation existante")
	void ajouterReservationTest11() throws OperationImpossible {
		// réservation initiale puis on la màj
		facade.ajouterUneReservation("f1", "e1", "c1", 1);
		facade.ajouterUneReservation("f1", "e1", "c1", 2) ;

		//post conditions
		Assertions.assertEquals(7, facade.getNbDisponibleCadeau("c1"));
		Assertions.assertEquals(5, facade.getNbPointsRestantsEnfant("f1", "e1"));
		Assertions.assertEquals(1, facade.listerLesReservationsDUneFamille("f1").size());
	}

	
	
// ajout d'un test en plus lié à l'ajout des notifications 

	@Test
	@DisplayName("test 12 : notification CE envoyée quand le stock atteint 0")
	void ajouterReservationTest12() throws OperationImpossible, InterruptedException {
		
		ConsommateurNotification consommateurCE = new ConsommateurNotification("CE");
		facade.enregistrerMembreCE(consommateurCE);

		facade.ajouterUnCadeau("cCE", "cadeau test notification CE", 1, 3);
		facade.ajouterUneReservation("f1", "e1", "cCE", 3) ;

		Thread.sleep(100);

		//donc on vérif que le CE a reçu exactement 1 notification mentionnant le cadeau épuisé
		Assertions.assertEquals(1, consommateurCE.getMessagesRecus().size());
		Assertions.assertTrue(consommateurCE.getMessagesRecus().get(0).contains("cCE"));
	}

}
