package telran.multithreading;

import java.util.concurrent.atomic.AtomicLong;

public class Truck extends Thread {
	
	private int load;
	private int nLoads;
	private static AtomicLong elevator1 = new AtomicLong();
	private static AtomicLong elevator2 = new AtomicLong();
	
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
	
	static private void loadElevator2(int load) {
		elevator2.addAndGet(load);
	}
	
	synchronized static private void loadElevator1(int load) {
		elevator1.addAndGet(load);
	}
	
	public static long getElevator1() {
		return elevator1.get();
	}
	
	public static long getElevator2() {
		return elevator2.get();
	}
}