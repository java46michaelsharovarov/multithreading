package telran.multithreading.executer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		List<OneGroupSum> groupSums = Arrays.stream(groups)
				.map(group -> new OneGroupSum(group)).toList();
		groupSums.forEach(executor::execute);
		waitingGroups(executor);
		var res = groupSums.stream().mapToLong(OneGroupSum::getRes).sum();
		System.out.printf("Runtime with %d threads is %d millis%n", numberOfThreads, System.currentTimeMillis() - startTime);
		return res;
	}
	
	private void waitingGroups(ExecutorService executor){
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			throw new IllegalStateException();
		}
	}
	
}
