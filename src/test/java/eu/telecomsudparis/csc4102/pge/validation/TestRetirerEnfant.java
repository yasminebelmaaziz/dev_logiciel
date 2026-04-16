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

class TestRetirerEnfant {

	private PGE facade;
	
	@BeforeEach
	void setUp() throws OperationImpossible {
		facade = new PGE(10000);
		
		facade.ajouterUneFamille("FAM", "famille test", new ConsommateurNotification("FAM")); 
		facade.ajouterUnEnfantAUneFamille("FAM", "nom", "prenom", "enf1");
		facade.ajouterUnCadeau("c1", "Jeu de billes", 5, 10);
		facade.ajouterUnCadeau("c2", "Jeu de billes", 5, 10);
		facade.ajouterUneReservation("FAM", "enf1", "c1", 1);
		
		
	}
	
	@AfterEach
	void tearDown() {
		facade = null;
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 1: id famille vide")
	void TestRetirerEnfant1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant(input, "enf1"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2: id enfant vide")
	void TestRetirerEnfant2(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant("FAM", input));
	}
	
	@Test
	@DisplayName("test 3: erreur la famille n'existe pas ")
	void TestRetirerEnfant3() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant("famille", "enf1"));
	}
	
	@Test
	@DisplayName("test 4 : erreur l'enfant n'existe pas ")
	void TestRetirerEnfant4() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant("FAM", "ouioui"));
	}

	
	@Test
	@DisplayName("test 5 : succès retrait enfant")
	void TestRetirerEnfant5() throws OperationImpossible {
		Assertions.assertEquals(9, facade.getNbDisponibleCadeau("c1"));

		facade.retirerUnEnfant("FAM", "enf1");

		// postconditions:
		Assertions.assertEquals(0, facade.listerLesEnfantsDUneFamille("FAM").size());

		Assertions.assertEquals(0, facade.listerLesReservationsDUneFamille("FAM").size());

		Assertions.assertEquals(10, facade.getNbDisponibleCadeau("c1"));
	}
	
	
	
//test ajouté pour les notifications : 
	@Test
	@DisplayName("test 6: notiif famille quand retrait d'un enfant libère un cadeau qui était épuisé et demandé")
	void TestRetirerEnfant6() throws OperationImpossible, InterruptedException {
		
		facade.ajouterUnEnfantAUneFamille("FAM", "Jackson", "M", "enf2");
		facade.ajouterUneReservation("FAM", "enf2", "c1", 9);
		Assertions.assertEquals(0, facade.getNbDisponibleCadeau("c1")); //cadeau épuiséé

		
		ConsommateurNotification consommateurFAM2 = new ConsommateurNotification("FAM2"); //autre fam demande une notif pour c1
		
		facade.ajouterUneFamille("FAM2", "Bibimbap", consommateurFAM2);
		facade.demanderUneNotificationDisponibilite("FAM2", "c1");

		// on retire enf1 qui avait réservé 1 C1
		facade.retirerUnEnfant("FAM", "enf1");
		
		Thread.sleep(100);

		//verif notif de la famille 2
		Assertions.assertEquals(1, consommateurFAM2.getMessagesRecus().size());
		Assertions.assertTrue(consommateurFAM2.getMessagesRecus().get(0).contains("c1"));
	}

}
