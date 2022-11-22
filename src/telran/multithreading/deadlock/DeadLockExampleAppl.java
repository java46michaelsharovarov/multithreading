package telran.multithreading.deadlock;

import java.util.stream.IntStream;

public class DeadLockExampleAppl {

	private static final int N_RUNS = 1;
	private static final int N_WORKERS = 2;

	public static void main(String[] args) throws InterruptedException {
		Worker.nRuns = N_RUNS;
		Worker[] workers = new Worker[N_WORKERS];
		startWorkers(workers);
	}

	private static void startWorkers(Worker[] workers) {
		IntStream.range(0, workers.length).forEach(i -> {
			workers[i] = new Worker();
			workers[i].start();//
		});

	}

}