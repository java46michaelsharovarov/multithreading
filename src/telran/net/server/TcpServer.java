package telran.net.server;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import telran.net.common.ApplProtocol;

public class TcpServer implements Runnable {

	private ServerSocket serverSocket;
	private int port;
	private ApplProtocol protocol;
	
	private static final int NUMBER_OF_THREADS = 2;
	public ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public TcpServer(int port, ApplProtocol protocol) throws Exception {
		this.port = port;
		this.protocol = protocol;
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				ClientSessionHandler clientServer = new ClientSessionHandler(socket, protocol);
				executor.execute(clientServer);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}