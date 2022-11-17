package telran.multithreading;

import java.util.Arrays;

public class PrinterControllerAppl {
	
	private static final int N_PRINTERS = 4;

	public static void main(String[] args) {
		Printer[] printers = createPrinters();	
		Arrays.stream(printers).forEach(Thread::start);
		printers[0].interrupt();
	}

	private static Printer[] createPrinters() {
		Printer[] printers = new Printer[N_PRINTERS];
		printers[0] =  new Printer(1);
		for(int i = 1; i < N_PRINTERS; i++) {
			printers[i] = new Printer(i + 1);
			printers[i - 1].setNextPrinter(printers[i]);
		}
		printers[N_PRINTERS - 1].setNextPrinter(printers[0]);
		return printers;
	}
	
}