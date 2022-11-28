package telran.multithreading.consumer;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {

	private MessageBox messageBox;
	
	public Receiver(MessageBox messageBox) {		
		setDaemon(true);//FIXME
		this.messageBox = messageBox;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				String message = messageBox.get();
				System.out.println(message + getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
