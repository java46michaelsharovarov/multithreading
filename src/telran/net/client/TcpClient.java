package telran.net.client;

import java.io.*;
import java.net.*;

import telran.net.common.Request;
import telran.net.common.Response;
import telran.net.common.ResponseCode;

public class TcpClient 
implements NetworkClient 
{
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public TcpClient(String hostname, int port) throws Exception{
		socket = new Socket(hostname, port);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T send(String requestType, Serializable requestData) {
		Request request = new Request(requestType, requestData);
		try {
			output.writeObject(request);
			Response response = (Response) input.readObject();
			if (response.code != ResponseCode.OK) {
				throw new Exception(response.responseData.toString());
			}
			return (T) response.responseData;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}		
	}

	@Override
	public void close() throws IOException {
		input.close();
		output.close();
		socket.close();
	}

}