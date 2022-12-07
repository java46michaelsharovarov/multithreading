package telran.net.client;

import java.io.*;

public interface NetworkClient extends Closeable{
	
	<T> T send(String requestType, Serializable requestData);
	
}