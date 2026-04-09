// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.pge;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class ConsommateurNotification implements Flow.Subscriber<String> {
	
	private String id;
	private Subscription souscription;

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
		souscription.request(1);
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
