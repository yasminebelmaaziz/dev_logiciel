package eu.telecomsudparis.csc4102.pge.unitaires;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.pge.Famille;

class TestFamille {

	private Famille famille;

	@BeforeEach
	void setUp() {
		famille = null;
	}

	@AfterEach
	void tearDown() {
		famille = null;
	}

	@Test
	@DisplayName("constructeur : id null")
	void constructeurTest1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille(null, "description"));
	}

	@Test
	@DisplayName("constructeur : id vide")
	void constructeurTest2() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("", "description"));
	}

	@Test
	@DisplayName("constructeur : description null")
	void constructeurTest3() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("idFamille", null));
	}

	@Test
	@DisplayName("constructeur : description vide")
	void constructeurTest4() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("idFamille", ""));
	}

	@Test
	@DisplayName("constructeur ok")
	void constructeurTest5() {
		famille = new Famille("idFamille", "description");
		Assertions.assertEquals("idFamille", famille.getId());
		Assertions.assertTrue(famille.invariant());
	}
}
