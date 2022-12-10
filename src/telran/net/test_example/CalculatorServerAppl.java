package telran.net.test_example;

import java.util.Scanner;

import telran.net.server.TcpServer;

public class CalculatorServerAppl {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in);) {
			TcpServer server = new TcpServer(CalculatorApi.PORT,
					new CalculatorProtocolController(new CalculatorServiceImpl()), 3);
			Thread thread = new Thread(server);
			thread.start();
			while(true) {
				System.out.println("for server shutdown type 'exit'");
				String enteredString = scanner.nextLine();
				if(enteredString.equals("exit")) {
					server.shutdown();
					System.out.println("the server has shut down");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}