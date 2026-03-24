// CHECKSTYLE:OFF

package eu.telecomsudparis.csc4102.pge.unitaires;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.pge.Enfant;

class TestEnfant {

	private Enfant enfant;

	@BeforeEach
	void setUp() {
		enfant = null;
	}

	@AfterEach
	void tearDown() {
		enfant = null;
	}

	@Test
	@DisplayName("constructeur : id null")
	void constructeurTest1() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Enfant(null, "Dupont", "Jean", 10));
	}

	@Test
	@DisplayName("constructeur : id vide")
	void constructeurTest2() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Enfant("", "Dupont", "Jean", 10));
	}

	@Test
	@DisplayName("constructeur : nom null")
	void constructeurTest3() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Enfant("e1", null, "Jean", 10));
	}

	@Test
	@DisplayName("constructeur : nom vide")
	void constructeurTest4() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Enfant("e1", "", "Jean", 10));
	}

	@Test
	@DisplayName("constructeur : prénom null")
	void constructeurTest5() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Enfant("e1", "Dupont", null, 10));
	}

	@Test
	@DisplayName("constructeur : prénom vide")
	void constructeurTest6() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Enfant("e1", "Dupont", "", 10));
	}

	@Test
	@DisplayName("constructeur ok")
	void constructeurTest7() {
		enfant = new Enfant("e1", "Dupont", "Jean", 10);
		Assertions.assertEquals("e1", enfant.getId());
		Assertions.assertTrue(enfant.invariant());
	}
}
