// CHECKSTYLE:OFF

package eu.telecomsudparis.csc4102.pge.unitaires;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.pge.ConsommateurNotification;
import eu.telecomsudparis.csc4102.pge.Famille;

class TestFamille {

	private Famille famille;
	private ConsommateurNotification consommateur;

	@BeforeEach
	void setUp() {
		famille = null;
		consommateur = new ConsommateurNotification("test");
	}

	@AfterEach
	void tearDown() {
		famille = null;
		consommateur = null;
	}

	@Test
	@DisplayName("constructeur : id null")
	void constructeurTest1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille(null, "description", consommateur));
	}

	@Test
	@DisplayName("constructeur : id vide")
	void constructeurTest2() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("", "description", consommateur));
	}

	@Test
	@DisplayName("constructeur : description null")
	void constructeurTest3() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("idFamille", null, consommateur));
	}

	@Test
	@DisplayName("constructeur : description vide")
	void constructeurTest4() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("idFamille", "", consommateur));
	}

	@Test
	@DisplayName("constructeur : consommateur null")
	void constructeurTest5Null() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Famille("idFamille", "description", null));
	}

	@Test
	@DisplayName("constructeur ok")
	void constructeurTest5() {
		famille = new Famille("idFamille", "description", consommateur);
		Assertions.assertEquals("idFamille", famille.getId());
		Assertions.assertTrue(famille.invariant());
	}
}
