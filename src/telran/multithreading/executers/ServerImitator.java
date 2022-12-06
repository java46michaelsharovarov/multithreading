package telran.multithreading.executers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImitator extends Thread {
	
	private static final int NUMBER_OF_THREADS = 10;
	private BlockingQueue<Request> queue;
	public ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public ServerImitator(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Request request = queue.take();
				executor.execute(request);
			} catch (InterruptedException e) {
				processRequests();
				break;
			}
		}
	}

	private void processRequests() {
		Request request = null;
		while ((request = queue.poll()) != null) {
			executor.execute(request);
		}
	}
	
}