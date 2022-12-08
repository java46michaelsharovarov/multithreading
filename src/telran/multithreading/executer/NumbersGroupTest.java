package telran.multithreading.executer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class NumbersGroupTest {

	private static final int NUMBER_OF_GROUPS = 15_000;
	private static final int NUMBER_OF_NUMBERS_IN_GROUP = 15_000;
	private static final int MIN_NUMBER = 0;
	private static final int MAX_NUMBER = 1_000;

	@Test
	void functionalTest() {
		int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		long expected = 45;
		NumberGroups numberGroups = new NumberGroups(array);
		assertEquals(expected, numberGroups.computeSum());
	}
	
	@Test
	void performanceTestDefaultNumberOfThread() {
		int[][] array = getGroups(NUMBER_OF_GROUPS, NUMBER_OF_NUMBERS_IN_GROUP);
		NumberGroups numberGroups = new NumberGroups(array);
		numberGroups.computeSum();
	}
	
	@Test
	void performanceTestOneThread() {
		int[][] array = getGroups(NUMBER_OF_GROUPS, NUMBER_OF_NUMBERS_IN_GROUP);
		NumberGroups numberGroups = new NumberGroups(array);
		numberGroups.setNumberOfThreads(1);
		numberGroups.computeSum();
	}
	
	@Test
	void performanceTestFourThreads() {
		int[][] array = getGroups(NUMBER_OF_GROUPS, NUMBER_OF_NUMBERS_IN_GROUP);
		NumberGroups numberGroups = new NumberGroups(array);
		numberGroups.setNumberOfThreads(4);
		numberGroups.computeSum();
	}
	
	@Test
	void performanceTestTenThreads() {
		int[][] array = getGroups(NUMBER_OF_GROUPS, NUMBER_OF_NUMBERS_IN_GROUP);
		NumberGroups numberGroups = new NumberGroups(array);
		numberGroups.setNumberOfThreads(10);
		numberGroups.computeSum();
	}

	@Test
	void performancefortyTenThreads() {
		int[][] array = getGroups(NUMBER_OF_GROUPS, NUMBER_OF_NUMBERS_IN_GROUP);
		NumberGroups numberGroups = new NumberGroups(array);
		numberGroups.setNumberOfThreads(40);
		numberGroups.computeSum();
	}	
	
	/*** Filling in the created array ***/	
	int[][] getGroups(int numberOfGroups, int numberOfNumbersInGroup) {
		int[][] array = new int[numberOfGroups][numberOfNumbersInGroup];
		Arrays.setAll(array, i -> new Random().ints(numberOfNumbersInGroup, MIN_NUMBER, MAX_NUMBER).toArray());
		return array;
	}
	
	int[][] getGroups1(int nGroups, int nNumbersInGroup) {
		return Stream.generate(() -> getGroup(nNumbersInGroup))
				.limit(nGroups).toArray(int[][]::new);
	}
	private int [] getGroup(int nNumbers) {
		return Stream.generate(() -> ThreadLocalRandom.current().nextInt())
				.limit(nNumbers).mapToInt(x->x).toArray();
	}
}
