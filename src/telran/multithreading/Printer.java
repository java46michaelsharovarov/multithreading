package telran.multithreading;

public class Printer extends Thread {
	
	private static final long TIMEOUT = Long.MAX_VALUE;
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
		int countOfLines = 0;
		while(countOfLines < numberOfLines) {			
			try {
				sleep(TIMEOUT);
			} catch (InterruptedException e) {
				System.out.println(Integer.toString(printerNumber).repeat(N_PORTIONS));
				countOfLines++;
				nextPrinter.interrupt();
			}
		}
	}
	
}