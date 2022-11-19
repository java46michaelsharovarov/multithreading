package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.stream.IntStream;

public class Racer extends Thread {
	
	static int distance;
	static Instant startTime;
	static int place;
	private static final Object mutex = new Object();
	private int racerNumber;
	private long runningTime;
	
	public Racer(int racerNumber) {
		this.racerNumber = racerNumber;
	}
	
	@Override
	public void run() {
		IntStream.range(0, distance).forEach(i -> {
			try {
				sleep(new Random().nextInt(4) + 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		assigningPlace();
	}

	private void assigningPlace() {
		synchronized (mutex) {
			runningTime = ChronoUnit.MILLIS.between(startTime, Instant.now());
			place += 1;
			placePrinting();
		}
	}

	private void placePrinting() {
		if(racerNumber > 9 && place > 9) {
			printingLineWithIndents(8, 8);
		} else if(racerNumber > 9) {
			printingLineWithIndents(9, 8);
		} else if(place > 9) {
			printingLineWithIndents(8, 9);
		} else {
			printingLineWithIndents(9, 9);
		}
	}

	private void printingLineWithIndents(int firstIndent, int secondIndent) {
		System.out.printf("  %d", place);
		System.out.printf(" ".repeat(firstIndent));
		System.out.printf("%d", racerNumber);
		System.out.printf(" ".repeat(secondIndent));
		System.out.printf("%d\n", runningTime);
	}
	
}