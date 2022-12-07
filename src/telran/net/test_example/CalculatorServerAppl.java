package telran.net.test_example;

import telran.net.server.TcpServer;

public class CalculatorServerAppl {

	public static void main(String[] args) {
		try {
			TcpServer server = new TcpServer(CalculatorApi.PORT,
					new CalculatorProtocolController(new CalculatorServiceImpl()));
			server.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}