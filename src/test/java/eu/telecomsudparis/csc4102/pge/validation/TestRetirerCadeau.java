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

public class TestRetirerCadeau {
	
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
	@DisplayName("test 1: id cadeau vide")
	void TestRetirerCadeau1(String input) {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnCadeau(input));
	}
	
	@Test
	@DisplayName("test 2: erreur le cadeau n'existe pas ")
	void TestRetirerCadeau2() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnCadeau("cadeauuu"));
	}
	
	@Test
	@DisplayName("test 3: erreur le cadeau est réservé ")
	void TestRetirerCadeau3() {
		Assertions.assertThrows(OperationImpossible.class, () -> facade.retirerUnCadeau("c1"));
	}
	
	@Test
	@DisplayName("test 4: le cadeau est retiré")
	void TestRetirerFamille4() throws OperationImpossible {
		facade.retirerUnCadeau("c2");
		// A FAIRE : quand on aura une fonction lister les RES
	}
	
}
