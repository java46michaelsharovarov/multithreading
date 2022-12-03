package telran.multithreading;

import java.util.concurrent.*;

public class Worker extends Thread {
	
	private BlockingQueue<Car> cars;
	private volatile boolean running = true;

	public Worker(BlockingQueue<Car> cars) {
		this.cars = cars;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		Car car = null;
		while (running) {
			try {
				car = cars.take();
				carService(car);
			} catch (InterruptedException e) {

			}
		}
		while ((car = cars.poll()) != null) {
			carService(car);
		}
	}

	private void carService(Car car) {
		try {
			sleep(car.getServiceTime());// service imitation
		} catch (InterruptedException e) {

		}
	}
	
}