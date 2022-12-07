package telran.multithreading.executer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

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
	void performanceTestDefaultNumberOfThreads() {
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
	void performanceTestTenThreads() {
		int[][] array = getGroups(NUMBER_OF_GROUPS, NUMBER_OF_NUMBERS_IN_GROUP);
		NumberGroups numberGroups = new NumberGroups(array);
		numberGroups.setNumberOfThreads(10);
		numberGroups.computeSum();
	}
	
	int[][] getGroups(int numberOfGroups, int numberOfNumbersInGroup) {
		int[][] array = new int[numberOfGroups][numberOfNumbersInGroup];
		Arrays.setAll(array, i -> new Random().ints(numberOfNumbersInGroup, MIN_NUMBER, MAX_NUMBER).toArray());
		return array;
	}
}
