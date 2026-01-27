// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.pge.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import eu.telecomsudparis.csc4102.pge.PGE;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestAjouterFamille {
	private PGE facade;

	@BeforeEach
	void setUp() {
		facade = new PGE(10);
	}

	@AfterEach
	void tearDown() {
		facade = null;
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("ajouter une famille erreur id null or vide")
	void ajouterUneFamilleTest1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneFamille(input, "description"));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("ajouter une famille erreur description null or vide")
	void ajouterUneFamilleTest2(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneFamille("idFamille", input));
	}

	@Test
	@DisplayName("add a family ok puis erreur existe déjà")
	void ajouterUneFamilleTest4Puis3() throws OperationImpossible {
		facade.ajouterUneFamille("idFamille", "description");
		Assertions.assertEquals(1, facade.listerLesFamilles().size());
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUneFamille("idFamille", "description"));
	}
}
