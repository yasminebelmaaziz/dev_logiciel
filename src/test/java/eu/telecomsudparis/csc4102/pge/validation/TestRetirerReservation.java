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
	
	// test des 2 scénarios (soit la supprésion ou la mise à jour d'une reservation

		@Test
		@DisplayName("test 10 : succès supprésion d'une réservation")
		void ajouterReservationTest10() throws OperationImpossible {
			facade.retirerUneReservation("f1", "e1", "c1", 2);
		}

		@Test
		@DisplayName("test 11 : succès mise à jour d'une réservation existante ")
		void ajouterReservationTest11() throws OperationImpossible {
			
			facade.retirerUneReservation("f1", "e1", "c1", 1);
		}

}
