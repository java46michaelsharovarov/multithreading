package telran.multithreading.executer;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class OneGroupSum implements Callable<Long> {

	private int[] group;

	public OneGroupSum(int[] group) {
		this.group = group;
	}

	@Override
	public Long call() {
		return Arrays.stream(group).asLongStream().sum();
	}

}
