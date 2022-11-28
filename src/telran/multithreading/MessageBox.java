package telran.multithreading;

public class MessageBox {

	private String message;

	public void put(String message) throws InterruptedException {
		synchronized (this) {
			while (this.message != null) {
				this.wait();
			}
			this.message = message;
			this.notifyAll();
		}
	}

	public String get() throws InterruptedException {
		synchronized (this) {
			while (message == null) {
				this.wait();
			}
			String res = message;
			message = null;
			this.notifyAll();
			return res;
		}
	}
	
}