package telran.multithreading;

public class Printer extends Thread {
	
	private static final long TIMEOUT = 1000;
	private static final int N_NUMBERS = 100;
	private static final int N_PORTIONS = 10;
	private int printerNumber;
	private Printer nextPrinter;
	private static int numberOfLines = N_NUMBERS / N_PORTIONS;

	public Printer(int printerNumber) { 
		this.printerNumber = printerNumber;
	}
	
	public void setNextPrinter(Printer nextPrinter) {
		this.nextPrinter = nextPrinter;
	}
	
	@Override
	public void run() {
		int index = 0;
		while(true) {			
			try {
				sleep(TIMEOUT);
			} catch (InterruptedException e) {
				portionPrinting();
				index++;
				nextPrinter.interrupt();
				if(index == numberOfLines) {
					break;
				}
			}
		}
	}

	private void portionPrinting() {
		for(int i = 0; i < N_PORTIONS; i++) {
			System.out.print(printerNumber);
		}
		System.out.println();
	}
	
}