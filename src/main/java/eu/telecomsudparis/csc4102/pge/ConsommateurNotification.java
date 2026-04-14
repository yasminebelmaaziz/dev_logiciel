package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class ConsommateurNotification implements Flow.Subscriber<String> {

	/**
	 * l'identitifiant.
	 */
	private String id;
	/**
	 * la souscription.
	 */
	private Subscription souscription;
	/**
	 * la liste des messages reçu.
	 */
	private List<String> messagesRecus = new ArrayList<>(); //on a décidé d'ajouter cette liste nous sert uniquement pour les test puissent faire des assertions dessus

	/**
	 * construit un consommateur de notification.
	 * 
	 * @param id	l'identifiant.
	 */
	public ConsommateurNotification(final String id) {
		this.id = id;
	}

	@Override
	public void onSubscribe(final Subscription souscription) {
		this.souscription = souscription;
		souscription.request(1);
	}

	@Override
	public void onNext(final String message) {
		System.out.println("Notification pour " + id + " : " + message);
		messagesRecus.add(message);
		souscription.request(1);
	}

	/**
	 * getter des messages reçus.
	 * 
	 * @return renvoie les messages reçus.
	 */
	public List<String> getMessagesRecus() {
		return messagesRecus;
	}

	@Override
	public void onError(final Throwable throwable) {
		throwable.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Fin des notifications pour " + id);
	}
}
