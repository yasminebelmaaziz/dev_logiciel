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
	@DisplayName("test 4 : nbDisponible incrémenté, nbInitial inchangé (cadeau non épuisé)")
	void TestReassortir4() throws OperationImpossible {
		
		facade.reassortir("c1", 1);
		
		// postconditions
		
		//nbDispo doit être ré incrémenté
		Assertions.assertEquals(10, facade.getNbDisponibleCadeau("c1"));
		//nbInitial mis à j aussi
		Assertions.assertEquals(11, facade.getNbInitialCadeau("c1"));
	}
	
	

	
	

}
