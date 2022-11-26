package telran.multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class TruckWithTwoMutexs extends Truck {

	private final static ReentrantLock lock = new ReentrantLock();
	private final static ReentrantLock lock1 = new ReentrantLock();

	public TruckWithTwoMutexs(Integer load, int nLoads) {
		super(load, nLoads);
	}

	@Override
	void loadElevator1(int load) {
		loadElevatorWithWaitingIterationCounter(load, 1, lock);
	}

	@Override
	void loadElevator2(int load) {
		loadElevatorWithWaitingIterationCounter(load, 2, lock1);
	}

	private void loadElevatorWithWaitingIterationCounter(int load, int elevatorNumber, ReentrantLock lock) {
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
