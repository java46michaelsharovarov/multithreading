package telran.net.test_example;

import telran.net.client.TcpClient;
import telran.view.ConsoleInputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CalculatorApp {

	public static void main(String[] args) {
		String hostName = (args.length > 0) ? args[0] : "localhost";
		try (TcpClient handler = new TcpClient(hostName, 3000)) {
			CalculatorService calculator = new NetCalculatorProxy(handler);
			Item[] items = CalculatorMenu.getCalculatorItems(calculator);			
			Menu menu = new Menu("Calculator for double numbers", 30, items);
			menu.perform(new ConsoleInputOutput());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
