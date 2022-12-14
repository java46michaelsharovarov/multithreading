package telran.net.client;

import java.io.*;
import java.net.*;

import telran.net.common.Request;
import telran.net.common.Response;
import telran.net.common.ResponseCode;

public class TcpClient 
implements NetworkClient {
	
	private String hostName;
	private int port;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public TcpClient(String hostname, int port) throws Exception{
		this.hostName = hostname;
		this.port = port;
		connect();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T send(String requestType, Serializable requestData) {
		Request request = new Request(requestType, requestData);
		boolean running = false;
		Response response = null;
		do {
			running = false;
			try {
				output.writeObject(request);
				response = (Response) input.readObject();
				if (response.code != ResponseCode.OK) {
					throw new Exception(response.responseData.toString());
				}
			} catch (Exception e) {
				if(e.getMessage().contains("host")) {
					System.out.println("waiting for reconnection");
					running = true;
					connect();
				} else {
					throw new RuntimeException(e.getMessage());
				}
			} 
		} while (running);
		return (T) response.responseData;
	}

	private void connect(){
		try {
			socket = new Socket(hostName, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void close() throws IOException {
		input.close();
		output.close();
		socket. close();
	}

}