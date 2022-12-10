package telran.net.server;

import java.net.*;

import telran.net.common.ApplProtocol;
import telran.net.common.Request;
import telran.net.common.Response;

import java.io.*;

public class ClientSessionHandler implements Runnable {

	private static final int READ_TIMEOUT = 100;
	private final Socket socket;
	private ApplProtocol protocol;
	private TcpServer tcpServer;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public ClientSessionHandler(Socket socket, ApplProtocol protocol, TcpServer tcpServer) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
		this.tcpServer = tcpServer;
		socket.setSoTimeout(READ_TIMEOUT);
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		System.out.println("client arrived:" + socket.getRemoteSocketAddress());
		while (!tcpServer.isShutdowned) {
			try {
				Request request = (Request) input.readObject();
				Response response = protocol.handlRequest(request);
				output.writeObject(response);
				output.reset();
			} catch (SocketTimeoutException e) {
				
			} catch (EOFException e) {
				System.out.printf("client %s closed connection\n", socket.getRemoteSocketAddress());
				break;
			} catch (Exception e) {
				System.out.printf("abnormal closing connection %s %s\n", socket.getRemoteSocketAddress(), e.getMessage());
				break;
			}
		}
		if(tcpServer.isShutdowned) {
			System.out.printf("client connection %s closed by server\n", socket.getRemoteSocketAddress());
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}