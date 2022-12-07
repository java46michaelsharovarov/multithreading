package telran.net.server;

import java.net.*;

import telran.net.common.ApplProtocol;
import telran.net.common.Request;
import telran.net.common.Response;

import java.io.*;

public class ClientSessionHandler implements Runnable {
	
	private final Socket socket;
	private ApplProtocol protocol;
	
	public ClientSessionHandler(Socket socket, ApplProtocol protocol) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try (	socket;
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				) {
			System.out.println("Client arrived:" + socket.getRemoteSocketAddress());
			while(true) {
				Request request = (Request) input.readObject();
				Response response = protocol.handlRequest(request);
				output.writeObject(response);
				output.reset();
			}
		} catch (EOFException e) {
			System.out.println("client closed connection");
		} catch (Exception e) {
			System.out.println("abnormal closing connection " + e.getMessage());
		}
	}

}