package telran.multithreading.deadlock;

public class Worker extends Thread {
	static Object resource1 = new Object();
	static Object resource2 = new Object();
	static Object resource3 = new Object();
	static int nRuns = 1;

	void f1() {
		synchronized (resource1) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			synchronized (resource2) {
				synchronized (resource3) {
				}
			}
		}
	}

	void f2() {
		synchronized (resource3) {
			synchronized (resource1) {
			}
		}
	}

	void f3() {
		synchronized (resource2) {				
			synchronized (resource1) {
			}
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			f1();
			f2();
			f3();
		}
	}
}