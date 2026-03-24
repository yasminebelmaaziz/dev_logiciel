// CHECKSTYLE:OFF


package eu.telecomsudparis.csc4102.pge.unitaires;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import eu.telecomsudparis.csc4102.pge.Cadeau;



class TestCadeau {

	private Cadeau cadeau;
	
	@BeforeEach
	void setUp(){
		
		cadeau= null;
	}
	
	@AfterEach
	void tearDown() {
		cadeau = null;
		
	}
	
	
	
	// tests constructeur
	
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test constructeur : id vide")
	void testConstructorId(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Cadeau(input, "un jouet", 10, 5));
	
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test constructeur : desc vide")
	void testConstructorDesc(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, () ->new Cadeau("c1", input, 10, 5));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,-5})
	@DisplayName("test constructeur : cout invalide ")
	void testConstructorCout(int input) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Cadeau("c1", "jouet",input, 5));
	}
	
	
	
	@Test
	@DisplayName("test constructeur: stock negatif")
	void testConstructorStock() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Cadeau("c1", "jouet",10,-1));
	}
	
	
	@Test
	@DisplayName("constructor ok")
	void constructeurTest5() {
		Cadeau cadeau = new Cadeau("idCadeau", "description", 10, 5);
		Assertions.assertEquals(10, cadeau.getNbDisponible());
		Assertions.assertEquals(5, cadeau.getCout());
	}
	
	
	
	// tests réserver (ie notre decrementerNbDisponible(int quantite))
	
	@Test
	@DisplayName("test decrementer : cas normal ")
	void testDecrementerOk() {
		cadeau = new Cadeau("c1", "jouet", 10, 5);
		cadeau.decrementerNbDisponible(2);
		Assertions.assertEquals(3, cadeau.getNbDisponible()) ;
	}
	
	@Test
	@DisplayName("test decrementer: trop d'un coup")
	void testDecrementerTrop() {
		cadeau = new Cadeau("c1", "jouet", 10, 2);
		
		Assertions.assertThrows(IllegalArgumentException.class, () ->cadeau.decrementerNbDisponible(5));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, -2})
	@DisplayName("test decrementer : quantite negative ou nulle ")
	void testDecrementerInvalide(int input) {
		cadeau = new Cadeau("c1", "jouet",10,5);
		Assertions.assertThrows(IllegalArgumentException.class, () -> cadeau.decrementerNbDisponible(input));
	}

}
