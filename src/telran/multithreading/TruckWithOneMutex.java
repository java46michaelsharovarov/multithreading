package telran.multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class TruckWithOneMutex extends Truck {

	private final static ReentrantLock lock = new ReentrantLock();

	public TruckWithOneMutex(Integer load, Integer nLoads) {
		super(load, nLoads);
	}

	@Override
	void loadElevator1(int load) {
		loadElevatorWithWaitingIterationCounter(load, 1);
	}

	@Override
	void loadElevator2(int load) {
		loadElevatorWithWaitingIterationCounter(load, 2);
	}

	private void loadElevatorWithWaitingIterationCounter(int load, int elevatorNumber) {
		while (!lock.tryLock()) {
			waitingIterationCounter.incrementAndGet();
		}
		try {
			if (elevatorNumber == 1) {
				elevator1 += load;
			} else if (elevatorNumber == 2) {
				elevator2 += load;
			}
		} finally {
			lock.unlock();
		}
	}

}