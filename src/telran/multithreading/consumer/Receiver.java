package telran.multithreading.consumer;

import java.util.concurrent.atomic.AtomicBoolean;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {

	private MessageBox messageBox;
	public static AtomicBoolean isEnd = new AtomicBoolean(false);
	
	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
	}
	
	@Override
	public void run() {
		while(!isEnd.get()) {
			try {
				String message = messageBox.get();
				System.out.println(message + getName());
			} catch (InterruptedException e) {
				isEnd.getAndSet(true);
			}
		}
	}
}
