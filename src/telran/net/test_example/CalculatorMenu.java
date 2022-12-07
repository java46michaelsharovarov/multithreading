package telran.net.test_example;

import telran.view.InputOutput;
import telran.view.Item;

public class CalculatorMenu {
	
	private static CalculatorService service;
	
	public static Item[] getCalculatorItems(CalculatorService service) {
		CalculatorMenu.service = service;
		Item[] res = {
				Item.of("Adding", CalculatorMenu::add),
				Item.of("Subtracting", CalculatorMenu::subtract),
				Item.of("Multiplying", CalculatorMenu::multiply),
				Item.of("Dividing", CalculatorMenu::divide),
				Item.exit()
			};
		return res;
	}
	
	private static void add(InputOutput io) {
		double numbers[] = enterNumbers(io);
		io.writeLine(service.add(numbers[0], numbers[1]));
	}

	private static void subtract(InputOutput io) {
		double numbers[] = enterNumbers(io);		
		io.writeLine(service.subtract(numbers[0], numbers[1]));
	}

	private static void divide(InputOutput io) {
		double numbers[] = enterNumbers(io);
		if(numbers[1] == 0) {
			io.writeLine("division by zero");
		} else {
			io.writeLine(service.divide(numbers[0], numbers[1]));
		}
	}
	
	private static void multiply(InputOutput io) {
		double numbers[] = enterNumbers(io);
		io.writeLine(service.multiply(numbers[0], numbers[1]));
	}
	
	private static double[] enterNumbers(InputOutput io) {
		double res[] = new double[2];
		res[0] = io.readDouble("enter first number", "no number");
		res[1] = io.readDouble("enter second number", "no number");
		return res;
	}
}
