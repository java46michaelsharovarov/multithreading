package telran.view;

public class NumbersOperationsMenu {
	
	public static Item getNumbersOperationsMenu() {
		return new Menu("Arithmetic operations", 7, getItems());
	}
	
	private static Item[] getItems() {
		Item[] res = {
			Item.of("Adding", NumbersOperationsMenu::add),
			Item.of("Subtracting", NumbersOperationsMenu::subtract),
			Item.of("Multiplying", NumbersOperationsMenu::multiply),
			Item.of("Dividing", NumbersOperationsMenu::divide),
			Item.exit()			
		};
		return res;
	}
	
	private static void add(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] + numbers[1]);
	}
	
	private static void subtract(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] - numbers[1]);
	}
	
	private static void multiply(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] * numbers[1]);
	}

	private static void divide(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] / numbers[1]);
	}
	
	private static int[] enterNumbers(InputOutput io) {
		int res[] = new int[2];
		res[0] = io.readInt("enter first number", "no number");
		res[1] = io.readInt("enter second number", "no number");
		return res;
	}
}
