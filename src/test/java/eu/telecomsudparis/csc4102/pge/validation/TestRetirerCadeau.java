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

public class TestRetirerCadeau {
	
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
	@DisplayName("test 1: id cadeau null ou vide")
	void TestRetirerCadeau1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnCadeau(input));
	}
	
	@Test
	@DisplayName("test 2: erreur le cadeau n'existe pas ")
	void TestRetirerCadeau2() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnCadeau("cadeauuu"));
	}
	
	@Test
	@DisplayName("test 3: erreur le cadeau est réservé ")
	void TestRetirerCadeau3() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnCadeau("c1"));
	}
	
	@Test
	@DisplayName("test 4: le cadeau est retiré du catalogue")
	void TestRetirerCadeau4() throws OperationImpossible {

		Assertions.assertEquals(2, facade.listerLesCadeaux().size());
		facade.retirerUnCadeau("c2");
		Assertions.assertEquals(1, facade.listerLesCadeaux().size());

	}
	
	
	//test ajouté pour la complétude
	// cahier des charges dit : "une demande de disponibilité disparaît aussi lorsque le cadeau est retiré du système"

	@Test
	@DisplayName("test  : la demande de notif est supp quand le cadeau est retiré du système")
	void TestRetirerCadeau5() throws OperationImpossible {
		facade.ajouterUnCadeau("c3", "cadeau vide" , 5, 0);

		
		ConsommateurNotification consommateurFAM2 = new ConsommateurNotification("FAM2");
		facade.ajouterUneFamille("FAM2", "laFamille", consommateurFAM2);
		facade.ajouterUnEnfantAUneFamille("FAM2", "lafamille", "ouioui", "enf2");
		facade.demanderUneNotificationDisponibilite("FAM2", "c3");

		Assertions.assertTrue(facade.familleSuitCadeau("FAM2", "c3"));
		
		facade.retirerUnCadeau("c3");
		
		Assertions.assertFalse(facade.familleSuitCadeau("FAM2", "c3"));

	}

}
