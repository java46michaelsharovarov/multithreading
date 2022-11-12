package telran.multithreading;

import java.util.Random;

public class Racer extends Thread {
	
	private int distance;
	private int racerNumber;
	private RaceMenu raceMenu;
	
	public Racer(RaceMenu raceMenu, int distance, int racerNumber) {
		this.distance = distance;
		this.racerNumber = racerNumber;
		this.raceMenu = raceMenu;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < distance; i++) {
			System.out.println(racerNumber);
			try {
				sleep(new Random().nextInt(4) + 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(raceMenu.winnerNumber == 0) {
			raceMenu.winnerNumber = racerNumber;
		}
	}
	
}