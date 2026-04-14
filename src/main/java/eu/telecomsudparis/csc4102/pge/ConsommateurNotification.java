// CHECKSTYLE:OFF

package eu.telecomsudparis.csc4102.pge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class ConsommateurNotification implements Flow.Subscriber<String> {

	private String id;
	private Subscription souscription;
	private List<String> messagesRecus = new ArrayList<>();

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
