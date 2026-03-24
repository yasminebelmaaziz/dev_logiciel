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
import eu.telecomsudparis.csc4102.pge.PGE;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestAjouterCadeau {

private PGE facade;
	
	@BeforeEach
	void setUp(){
		facade= new PGE(10);
	}
	
	@AfterEach
	void tearDown() {
		facade= null ;
	}
	
	
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 1: id null ou vide")
	void ajouterUnCadeauTest1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnCadeau(input,  "un cadeau", 5, 10));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2 : description null ou vide")
	void ajouterUnCadeauTest2(String input) {
		Assertions.assertThrows(OperationImpossible.class ,() -> facade.ajouterUnCadeau("c1",input, 5, 10));
	}
	
	@Test
	@DisplayName("test 3 : cadeau deja existant")
	void ajouterUnCadeauTest3() throws OperationImpossible {
		facade.ajouterUnCadeau("c1", "jouet", 5, 10);
	// ca doit planter si on remet le meme id
	Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnCadeau("c1", "jouet",5, 10));
	}
	
	
	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	@DisplayName("test 4: cout invalide")
	void ajouterUnCadeauTest4(int input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnCadeau("c1", "jouet", input,10) );
	}
	
	@Test
	@DisplayName("test 5 : nbInitial negatif")
	void ajouterUnCadeauTest5(){
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnCadeau("c1", "jouet",  5,  -1));
	}
	
	
	
	@Test
	@DisplayName("test 6: cas nominal ok")
	void ajouterUnCadeauTest6() throws OperationImpossible{
		facade.ajouterUnCadeau("c1", "cadeau ok", 5, 10);
	}

}
