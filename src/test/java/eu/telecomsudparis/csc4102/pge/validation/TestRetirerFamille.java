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
		Assertions.assertEquals(0, facade.listerLesFamilles().size());
	}

	@Test
	@DisplayName("test 4: les enfants de la famille ont bien étés supprimés")
	void TestRetirerFamille4() throws OperationImpossible {
		facade.retirerUneFamille("FAM");
		Assertions.assertEquals(0, facade.listerLesEnfants().size());
	}

	@Test
	@DisplayName("test 5: le nbDisponible des cadeaux réservés a bien été ré-incrémenté")
	void TestRetirerFamille5() throws OperationImpossible {

		//enf1 a réservé 1 exemplaire de c1
		Assertions.assertEquals(9, facade.getNbDisponibleCadeau("c1"));

		facade.retirerUneFamille("FAM");
		//toutes les réservations de enf1 sont annulées donc nbDispo revient à 10
		Assertions.assertEquals(10, facade.getNbDisponibleCadeau("c1"));
	}

	
	
//test ajouté pour les notifications : 
	@Test
	@DisplayName(" test 6 : notif quand une famille est supprimée et que ça libère un cadeau épuisé")
	void TestRetirerFamille6() throws OperationImpossible, InterruptedException {

		facade.ajouterUnEnfantAUneFamille("FAM", "nonon", "ouioui", "enf2");
		facade.ajouterUneReservation("FAM", "enf2", "c1", 9);
		Assertions.assertEquals(0, facade.getNbDisponibleCadeau("c1"));

		ConsommateurNotification consommateurFAM2 = new ConsommateurNotification("FAM2");
		facade.ajouterUneFamille("FAM2", "bidule", consommateurFAM2 );
		facade.demanderUneNotificationDisponibilite("FAM2" , "c1");  //autre fam demande une notif pour c1

		// on retire la famille qui avait TOUTES les réservations (ça faisait beaucoup..) ===>>> c1 redevient dispo
		facade.retirerUneFamille("FAM");
		Thread.sleep(100);

		Assertions.assertEquals(1, consommateurFAM2.getMessagesRecus().size());
		Assertions.assertTrue(consommateurFAM2.getMessagesRecus().get(0).contains("c1"));  //la 2e famille reçoit la bonnne notif
	}

}
