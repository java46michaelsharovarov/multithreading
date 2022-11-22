package telran.multithreading.deadlock;

public class Worker extends Thread {
	static Object resource1 = new Object();
	static Object resource2 = new Object();
	static Object resource3 = new Object();
	static int nRuns = 1;

	void f1() {
		synchronized (resource1) {
			synchronized (resource2) {
				synchronized (resource3) {
				}
			}
		}
	}

	void f2() {
		synchronized (resource1) {
			synchronized (resource3) {
			}
		}
	}

/**************** Solution with the addition of code ****************/		
	void f3() {
		synchronized (resource2) {
			synchronized (resource3) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (resource1) {
				}			
			}
		}
	}

/******************** Solution with code change  ********************/	
	void f4() {
		synchronized (resource2) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
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
			f4();
		}
	}
}