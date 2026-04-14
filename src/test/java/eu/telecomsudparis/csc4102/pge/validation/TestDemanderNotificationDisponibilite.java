// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.pge.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import eu.telecomsudparis.csc4102.pge.ConsommateurNotification;
import eu.telecomsudparis.csc4102.pge.PGE;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

public class TestDemanderNotificationDisponibilite {

	private PGE facade;
	private ConsommateurNotification consommateurF1;

	@BeforeEach
	void setUp() throws OperationImpossible {
		
		facade = new PGE(10000);
		
		consommateurF1 = new ConsommateurNotification("f1");
		facade.ajouterUneFamille("f1", "Famille Dupont", consommateurF1);
		facade.ajouterUnEnfantAUneFamille("f1", "Dupont", "Alice", "e1");
		 
		facade.ajouterUnCadeau("c1", "Jeu de billes", 1, 5); //cadeau épuisé
		facade.ajouterUneReservation("f1", "e1", "c1", 5);

		facade.ajouterUnCadeau("c2", "Vélo", 3, 10); //cadeau pas épuisé 
	}

	@AfterEach
	void tearDown() {
		facade = null;
		consommateurF1 = null;
	}


	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 1 : erreur idFamille null ou vide")
	void test1(String input) {
		Assertions.assertThrows(OperationImpossible.class,
				() -> facade.demanderUneNotificationDisponibilite(input, "c1"));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2: erreur idCadeau null ou vide ")
	void test2(String input) {
		Assertions.assertThrows(OperationImpossible.class, ()-> facade.demanderUneNotificationDisponibilite("f1", input)) ;
	}

	@Test
	@DisplayName("test 3 : erreur la famille n'existe pas")
	void test3() {
		Assertions.assertThrows(OperationImpossible.class,	()-> facade.demanderUneNotificationDisponibilite("f_inconnu" , "c1"));
	}

	@Test
	@DisplayName("Test 4: erreur le cadeau n'existe pas")
	void test4() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.demanderUneNotificationDisponibilite("f1", "NOOONN"));
	}

	@Test
	@DisplayName("test 5 : erreur le cadeau est encore disponible (nbDisponible > 0)")
	void test5() {
		Assertions.assertThrows(OperationImpossible.class, ()-> facade.demanderUneNotificationDisponibilite("f1", "c2"));
	}


	@Test
	@DisplayName(" Test 6 : succès, la famille reçoit une notification quand le cadeau redevient disponible")
	void test6() throws OperationImpossible, InterruptedException {
		ConsommateurNotification consommateurF2 = new ConsommateurNotification("f2");
		facade.ajouterUneFamille("f2", "Famille Martin", consommateurF2) ;

		//c1 est épuisé f2 demande une notification
		facade.demanderUneNotificationDisponibilite("f2", "c1");

		// on a libéré 1 unité  => donc le cadeau redevient dispo
		facade.retirerUneReservation("f1", "e1", "c1", 1);
		Thread.sleep(100);

		//f2 a bien reçu une notification pour c1
		Assertions.assertEquals(1, consommateurF2.getMessagesRecus().size()) ;
		Assertions.assertTrue(consommateurF2.getMessagesRecus().get(0).contains("c1"));
	}
}
