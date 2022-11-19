package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.stream.IntStream;

public class Racer extends Thread {
	
	private int distance;
	private int racerNumber;
	private RaceMenu raceMenu;
	long runningTime;
	
	public Racer(RaceMenu raceMenu, int distance, int racerNumber) {
		this.distance = distance;
		this.racerNumber = racerNumber;
		this.raceMenu = raceMenu;
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
		runningTime = ChronoUnit.MILLIS.between(raceMenu.startTime, Instant.now());
		raceMenu.place = ++raceMenu.place;
		System.out.printf("  %d         %d         %d\n", raceMenu.place, racerNumber, runningTime);
	}
	
}