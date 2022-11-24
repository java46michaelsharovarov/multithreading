package telran.multithreading.atomic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.stream.IntStream;

public class Racer extends Thread {
	
	static int distance;
	static Instant startTime;
	private int racerNumber;
	private long runningTime;
	private RaceMenu race;
	
	public Racer(int racerNumber, RaceMenu race) {
		this.racerNumber = racerNumber;
		this.race = race;
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
		race.winnerNumber.compareAndSet(0, racerNumber);
	}

	private void assigningPlace() {
		synchronized (Racer.class) {
			runningTime = ChronoUnit.MILLIS.between(startTime, Instant.now());
			race.resultsTable.add(this);
		}
	}

	public int getRacerNumber() {
		return racerNumber;
	}

	public long getRunningTime() {
		return runningTime;
	}
	
}