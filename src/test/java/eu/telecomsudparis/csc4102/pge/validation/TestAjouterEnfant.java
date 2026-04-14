// CHECKSTYLE:OFF

package eu.telecomsudparis.csc4102.pge.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import eu.telecomsudparis.csc4102.pge.ConsommateurNotification;
import eu.telecomsudparis.csc4102.pge.PGE;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestAjouterEnfant {

	private PGE facade;
	
	@BeforeEach
	void setUp() throws OperationImpossible {
		facade = new PGE(20);
		
		facade.ajouterUneFamille("FAM", "famille test", new ConsommateurNotification("FAM")); //besoin d'une famille pour ajouter un enfant dedans..
	}
	
	@AfterEach
	void tearDown() {
		facade = null;
	}
	
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 1: id famille vide")
	void ajouterEnfantTest1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille(input,"nom", "prenom","e1"));
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2 : nom vide")
	
	void ajouterEnfantTest2(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille("FAM", input, "prenom", "e1")) ;
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 3: prenom vide")
	void ajouterEnfantTest3(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille("FAM", "nom", input , "e1"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 4 : id enfant vide")
	void ajouterEnfantTest4(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille("FAM", "nom", "prenom" , input));
	
	}
	
	
	@Test
	@DisplayName("test 5: famille existe pas")
	void ajouterEnfantTest5() {
		
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille( "introuvable" , "nom", "prenom", "e1"));
	}
	
	
	
	@Test
	@DisplayName("test 6: id enfant deja pris")
	void ajouterEnfantTest6() throws OperationImpossible {
		facade.ajouterUnEnfantAUneFamille("FAM", "nom1" , "prenom1", "e1");
		
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille("FAM" , "nom2","prenom2", "e1"));
	}
	
	@Test
	@DisplayName("test 7 : tout est ok")
	void ajouterEnfantTest7() throws OperationImpossible {
		
		facade.ajouterUnEnfantAUneFamille("FAM", "oui", "non", "e1");
		
		
		// postconditions : l enfant est present dans la famille puis que le solde de points est initialisé au max
		Assertions.assertEquals(1 , facade.listerLesEnfantsDUneFamille("FAM").size());
		Assertions.assertEquals(20 , facade.getNbPointsRestantsEnfant("FAM", "e1")) ;
	}

}
