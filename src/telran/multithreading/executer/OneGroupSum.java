package telran.multithreading.executer;

import java.util.Arrays;

public class OneGroupSum implements Runnable {

	private int[] droup;
	private long res;

	public OneGroupSum(int[] droup) {
		this.droup = droup;
	}

	@Override
	public void run() {
		res = Arrays.stream(droup).sum();
	}

	public long getRes() {
		return res;
	}

}
