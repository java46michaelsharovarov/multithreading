package telran.multithreading.executer;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public long computeSum(){
		ArrayList<OneGroupSum> array = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		
		Arrays.stream(groups).forEach(g -> array.add(new OneGroupSum(g)));
		array.stream().forEach(e -> executor.execute(e));
		try {
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return array.stream().mapToLong(e-> e.getRes()).sum(); 
	}
	
}
