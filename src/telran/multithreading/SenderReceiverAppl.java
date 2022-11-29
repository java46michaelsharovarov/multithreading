package telran.multithreading;

import java.util.stream.IntStream;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.producer.Sender;

public class SenderReceiverAppl {

	private static final int N_RECEIVERS = 10;
	private static final int N_MESSAGES = 1500;

	public static void main(String[] args) throws InterruptedException {
		
		Receiver[] receivers = new Receiver[N_RECEIVERS];
		MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		
		startReceivers(receivers, messageBox);
		sender.start();
		sender.join();
		interruptingReceivers(receivers);
		System.out.println(Receiver.getMessagesCounter());
	}

	private static void interruptingReceivers(Receiver[] receivers) {
		IntStream.range(0, N_RECEIVERS)
		.forEach(i -> receivers[i].interrupt());		
	}

	private static void startReceivers(Receiver[] receivers, MessageBox messageBox) {
		IntStream.range(0, N_RECEIVERS)
		.forEach(i -> {
			receivers[i] = new Receiver(messageBox);
			receivers[i].start();
		});
	}
	
}
