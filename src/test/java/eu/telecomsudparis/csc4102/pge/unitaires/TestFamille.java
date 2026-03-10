// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.pge.unitaires;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import eu.telecomsudparis.csc4102.pge.Famille;

class TestFamille {
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("constructeur erreur null or empty id")
	void constructeurTest1(final String input) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille(input, "description"));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("constructeur erreur null or empty description")
	void constructeurTest2(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("idFamille", input));
	}

	@Test
	@DisplayName("constructor ok")
	
	
	void constructeurTest3() {
		
		Famille famille = new Famille("idFamille", "description");
		Assertions.assertEquals("idFamille", famille.getId());
	}
}
