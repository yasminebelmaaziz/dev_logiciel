// CHECKSTYLE:OFF

package eu.telecomsudparis.csc4102.pge.validation;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.pge.PGE;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestListerEnfants {

	private PGE facade;

	@BeforeEach
	void setUp() {
		facade = new PGE(100);
	}

	@AfterEach
	void tearDown() {
		facade = null;
	}

	@Test
	@DisplayName("test 1 : aucun enfant dans le système, donc liste vide")
	void listerLesEnfantsTest1() {
		List<String> enfants = facade.listerLesEnfants();
		Assertions.assertNotNull(enfants);
		Assertions.assertEquals(0, enfants.size());
	}

	@Test
	@DisplayName("test 2 : un enfant dans une famille donc liste de taille 1")
	void listerLesEnfantsTest2() throws OperationImpossible {
		facade.ajouterUneFamille("FAM1" , "famille test");
		facade.ajouterUnEnfantAUneFamille("FAM1", "Dupont", "Alice", "enf1");

		List<String> enfants = facade.listerLesEnfants();
		Assertions.assertNotNull(enfants);
		Assertions.assertEquals(1, enfants.size());
		Assertions.assertTrue(enfants.get(0).contains("enf1"));
	}

	@Test
	@DisplayName("test 3 : plusieurs enfants dans plusieurs familles")
	void listerLesEnfantsTest3() throws OperationImpossible {
		facade.ajouterUneFamille("FAM1" , "famille un");
		facade.ajouterUneFamille("FAM2" , "famille deux");
		
		facade.ajouterUnEnfantAUneFamille("FAM1", "Dupont", "Alice", "enf1");
		facade.ajouterUnEnfantAUneFamille("FAM1", "Dupont", "Bob", "enf2");
		facade.ajouterUnEnfantAUneFamille("FAM2", "Nonnon", "ouioui", "enf3") ;

		List<String> enfants = facade.listerLesEnfants();
		
		Assertions.assertNotNull(enfants);
		Assertions.assertEquals(3, enfants.size());
		
	}
}
