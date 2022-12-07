package telran.multithreading.executers;

import java.util.List;
import java.util.concurrent.*;

public class ServerClientControllerAppl {

	private static final int NUMBER_OF_REQUESTS = 1_000_000;

	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
		ClientImitator client = new ClientImitator(NUMBER_OF_REQUESTS, queue);
		ServerImitator server = new ServerImitator(queue);
		
		client.start();
		server.start();
		client.join();
		server.interrupt();

		server.executor.awaitTermination(10, TimeUnit.SECONDS);
		List<Runnable> unprocessedRequests = server.executor.shutdownNow();
		
		server.executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

		System.out.println("counter of the processed requests is " + Request.getRequestCounter());
		System.out.println("counter of the cancelled requests is " + Request.getCancelledCounter());
		System.out.println("counter of the unprocessed requests is " + unprocessedRequests.size());

	}

}