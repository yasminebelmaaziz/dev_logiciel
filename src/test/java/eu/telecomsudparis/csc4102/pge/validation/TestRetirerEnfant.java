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

class TestRetirerEnfant {

	private PGE facade;
	
	@BeforeEach
	void setUp() throws OperationImpossible {
		facade = new PGE(10000);
		
		facade.ajouterUneFamille("FAM", "famille test"); 
		facade.ajouterUnEnfantAUneFamille("FAM", "nom", "prenom", "enf1");
		facade.ajouterUnCadeau("c1", "Jeu de billes", 5, 10);
		facade.ajouterUnCadeau("c2", "Jeu de billes", 5, 10);
		facade.ajouterUneReservation("FAM", "enf1", "c1", 1);
		
		
	}
	
	@AfterEach
	void tearDown() {
		facade = null;
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 1: id famille vide")
	void TestRetirerEnfant1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant(input, "enf1"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("test 2: id enfant vide")
	void TestRetirerEnfant2(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant("FAM", input));
	}
	
	@Test
	@DisplayName("test 3: erreur la famille n'existe pas ")
	void TestRetirerEnfant3() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant("famille", "enf1"));
	}
	
	@Test
	@DisplayName("test 4 : erreur l'enfant n'existe pas ")
	void TestRetirerEnfant4() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnEnfant("FAM", "ouioui"));
	}

	
	@Test
	@DisplayName("test 5 : succès retrait enfant")
	void TestRetirerEnfant5() throws OperationImpossible {
		facade.retirerUnEnfant("FAM", "enf1");
		
		//2e test : vérification de ré incrémentation du nombre de cadeaux disponibles
		
		facade.ajouterUnEnfantAUneFamille("FAM", "nouveau", "enfant", "enf2");
		facade.ajouterUneReservation("FAM", "enf2", "c1", 2);
		
		
	}
	

}