package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterControllerAppl {

	public static void main(String[] args) throws InterruptedException {
		Printer printer1 = new Printer(100, '*');
		Printer printer2 = new Printer(100, '#');
		Instant start = Instant.now();
		printer1.start();
		printer2.start();
		printer1.join(); //waiting for finishing thread printer1
		printer2.join(); //waiting for finishing thread printer2
		System.out.printf("running time is %d\n",
				ChronoUnit.MILLIS.between(start, Instant.now()));
	}

}