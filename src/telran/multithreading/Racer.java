package telran.multithreading;

import java.util.Random;

public class Racer extends Thread {
	
	private int distance;
	private int racerNumber;
	
	public Racer(int distance, int racerNumber) {
		this.distance = distance;
		this.racerNumber = racerNumber;
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
		if(RaceMenu.winnerNumber == 0) {
			RaceMenu.winnerNumber = racerNumber;
		}
	}
	
}