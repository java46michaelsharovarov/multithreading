package telran.net.server;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import telran.net.common.ApplProtocol;

public class TcpServer implements Runnable {

	private ServerSocket serverSocket;
	private int port;
	private ApplProtocol protocol;
	private static final int DEFAULT_NUMBER_OF_THREADS = 5;
	int currentNumberOfThreads;
	private static final int ACCEPT_TIMEOUT = 100;
	private ExecutorService executor;
	volatile boolean isShutdowned = false;
	AtomicInteger clientConnectionCounter = new AtomicInteger(0);

	public TcpServer(int port, ApplProtocol protocol, int numberOfThreads) throws Exception {
		this.port = port;
		this.protocol = protocol;
		serverSocket = new ServerSocket(port);
		currentNumberOfThreads = numberOfThreads;
		executor = Executors.newFixedThreadPool(numberOfThreads);
		serverSocket.setSoTimeout(ACCEPT_TIMEOUT);
	}

	public TcpServer(int port, ApplProtocol protocol) throws Exception {
		this(port, protocol, DEFAULT_NUMBER_OF_THREADS);
	}

	@Override
	public void run() {
		System.out.println("server is listening on the port " + port);
		while (!isShutdowned) {
			try {
				Socket socket = serverSocket.accept();
				clientConnectionCounter.incrementAndGet();
				ClientSessionHandler clientServer = new ClientSessionHandler(socket, protocol, this);
				executor.execute(clientServer);
			} catch (SocketTimeoutException e) {

			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		executor.shutdown();
	}

	public void shutdown() {
		isShutdowned = true;
	}

}