package telran.multithreading;

public class Printer extends Thread {
	
	private int nPrints;
	private char symb;
	
	public Printer(int nPrints, char symb) {
		this.nPrints = nPrints;
		this.symb = symb;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < nPrints; i++) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(symb);
		}
	}
	
}