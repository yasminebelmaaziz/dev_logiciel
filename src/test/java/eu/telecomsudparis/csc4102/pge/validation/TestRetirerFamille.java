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

class TestRetirerFamille {

	private PGE facade;
	
	@BeforeEach
	void setUp() throws OperationImpossible {
		facade = new PGE(10000);
		
		facade.ajouterUneFamille("FAM", "famille test", new ConsommateurNotification("FAM")); 
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
	void TestRetirerFamille1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneFamille(input));
	}
	
	@Test
	@DisplayName("test 2: erreur la famille n'existe pas ")
	void TestRetirerFamille2() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUneFamille("LAFAMILLE"));
	}

	
	
	@Test
	@DisplayName("test 3: la famille a bien été supprimée")
	void TestRetirerFamille3() throws OperationImpossible {
		facade.retirerUneFamille("FAM");
		
		//on essaye d'ajouter un enfant à famille après l'avoir supp pour vérifier que la famille n'existe plus 
		Assertions.assertThrows(OperationImpossible.class, () -> facade.ajouterUnEnfantAUneFamille("FAM", "nouveau", "enfant", "enf2"));
	}
	
	@Test
	@DisplayName("test 4: les enfants de la famille ont bien étés supprimés")
	void TestRetirerFamille4() throws OperationImpossible {
		facade.retirerUneFamille("FAM");
		// A FAIRE : quand on aura une fonction lister les enfants
	}
	
	
	@Test
	@DisplayName("test 5: les réservations de la famille ont bien étés supprimées")
	void TestRetirerFamille5() throws OperationImpossible {
		facade.retirerUneFamille("FAM");
		// A FAIRE : quand on aura une fonction lister les RES
	}
	
	

}
