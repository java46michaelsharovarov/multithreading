package telran.multithreading;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Truck extends Thread {

	private int load;
	private int nLoads;
	protected static long elevator1;
	protected static long elevator2;
	protected static AtomicLong waitingIterationCounter = new AtomicLong(0);

	public Truck(int load, int nLoads) {
		this.load = load;
		this.nLoads = nLoads;
	}

	@Override
	public void run() {
		for (int i = 0; i < nLoads; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}

	abstract void loadElevator1(int load);

	abstract void loadElevator2(int load);

	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}

	public static long getWaitingIterationCounter() {
		return waitingIterationCounter.get();
	}
}