package telran.multithreading;

import java.util.concurrent.*;

public class MessageBox {
	
	BlockingQueue<String> queue = new MyBlockingQueueImpl<>(1000);

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