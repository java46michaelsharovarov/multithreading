package telran.multithreading.executers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ClientImitator extends Thread {
	
	private static final int MIN_REQUEST_TIMEOUT = 2000;
	private static final int MAX_REQUEST_TIMEOUT = 3000;
	private int numberOfRequests;
	private BlockingQueue<Request> queue;

	public ClientImitator(int numberOfRequests, BlockingQueue<Request> queue) {
		this.numberOfRequests = numberOfRequests;
		this.queue = queue;
	}

	@Override
	public void run() {
		IntStream.range(0, numberOfRequests).forEach(i -> {
			Request request = new Request(getRequestTimeout());
			try {
				queue.put(request);
			} catch (InterruptedException e) {
				
			}
		});
	}

	private long getRequestTimeout() {
		return ThreadLocalRandom.current()
				.nextInt(MIN_REQUEST_TIMEOUT, MAX_REQUEST_TIMEOUT);
	}
	
}