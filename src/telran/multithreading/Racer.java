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
			System.out.printf("%3d\t%7d\t\t%d\n", place, racerNumber, runningTime);
		}
	}
	
}