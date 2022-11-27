package telran.multithreading;

public class MessageBox {

	private String message;

	public void put(String message) {
		synchronized (this) {
			this.message = message;
			this.notify();
		}
	}

	public String get() throws InterruptedException {
		synchronized (this) {
			while (message == null) {
				this.wait();
			}
			String res = message;
			message = null;
			return res;
		}

	}
}