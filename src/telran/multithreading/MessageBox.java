package telran.multithreading;

import java.util.concurrent.*;

public class MessageBox {
	
	BlockingQueue<String> queue = new LinkedBlockingQueue<>(1000); // FIXME replace with MyBlockingQueueImpl

	public void put(String message) throws InterruptedException {
		queue.put(message);
	}

	public String take() throws InterruptedException {
		return queue.take();
	}

	public String poll() {
		return queue.poll();
	}

}