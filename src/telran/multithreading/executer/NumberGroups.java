package telran.multithreading.executer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NumberGroups {

	private int[][] groups;
	private int numberOfThreads = 4;
	
	public NumberGroups(int[][] groups) {
		this.groups = groups;
	}
	
	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	} 
	
	public long computeSum() {
		long startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		List<Future<Long>> groupSums = Arrays.stream(groups).map(OneGroupSum::new)
				.map(executor::submit).toList();
		long res = groupSums.stream().mapToLong(future -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new IllegalStateException();
			}
		}).sum();
		System.out.printf("Runtime with %d threads is %d millis%n", numberOfThreads, System.currentTimeMillis() - startTime);
		return res;
	}
		
}
