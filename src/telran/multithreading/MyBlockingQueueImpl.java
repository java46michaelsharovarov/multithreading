package telran.multithreading;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class MyBlockingQueueImpl<E> implements BlockingQueue<E> {

	private List<E> queue = new LinkedList<>();
	private int capacity;
	private ReentrantLock mutex = new ReentrantLock();
	private Condition producersWaitingCondition = mutex.newCondition();
	private Condition consumersWaitingCondition = mutex.newCondition();

	public MyBlockingQueueImpl(int capacity) {
		this.capacity = capacity;
	}

	public MyBlockingQueueImpl() {
		this(Integer.MAX_VALUE);
	}

	@Override
	public E element() {
		mutex.lock();
		try {
			if (queue.isEmpty()) {
				throw new NoSuchElementException();
			}
			return queue.get(0);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E peek() {
		mutex.lock();
		try {
			return queue.isEmpty() ? null : queue.get(0);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public int size() {
		mutex.lock();
		try {
			return queue.size();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		mutex.lock();
		try {
			return queue.isEmpty();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		mutex.lock();
		try {
			return queue.iterator();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public Object[] toArray() {
		mutex.lock();
		try {
			return queue.toArray();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		mutex.lock();
		try {
			return queue.toArray(a);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		mutex.lock();
		try {
			return queue.containsAll(c);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		mutex.lock();
		try {
			int prevSize = size();
			c.forEach(this::add);
			return prevSize != size();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		mutex.lock();
		try {
			if (queue.size() == capacity) {
				producersWaitingCondition.signal();
			}
			return queue.removeAll(c);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		mutex.lock();
		try {
			boolean res = queue.retainAll(c);
			if (res) {
				producersWaitingCondition.signal();
			}
			return res;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public void clear() {
		mutex.lock();
		try {
			queue.clear();
			producersWaitingCondition.signal();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		mutex.lock();
		try {
			while (queue.size() == capacity) {
				producersWaitingCondition.await();
			}
			queue.add(e);
			if (queue.size() == 1) {
				consumersWaitingCondition.signal();
			}
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		if (e == null || timeout < 0) {
			throw new IllegalArgumentException();
		}
		long nanos = unit.toNanos(timeout);
		mutex.lock();
		try {
			while (queue.size() == capacity) {
				if (nanos <= 0)
					return false;
				nanos = producersWaitingCondition.awaitNanos(nanos);
			}
			queue.add(e);
			if (queue.size() == 1) {
				consumersWaitingCondition.signal();
			}
			return true;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		try {
			return offer(e, 0, TimeUnit.NANOSECONDS);
		} catch (InterruptedException exp) {

		}
		return false;
	}

	@Override
	public boolean add(E e) {
		mutex.lock();
		try {
			if (queue.size() == capacity) {
				throw new IllegalStateException();
			}
			queue.add(e);
			if (queue.size() == 1) {
				consumersWaitingCondition.signal();
			}
			return true;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		mutex.lock();
		try {
			while (queue.isEmpty()) {
				consumersWaitingCondition.await();
			}
			if (queue.size() == capacity) {
				producersWaitingCondition.signal();
			}
			return queue.remove(0);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		if (timeout < 0) {
			throw new IllegalArgumentException();
		}
		long nanos = unit.toNanos(timeout);
		mutex.lock();
		try {
			while (queue.isEmpty()) {
				if (nanos <= 0)
					return null;
				nanos = consumersWaitingCondition.awaitNanos(nanos);
			}
			if (queue.size() == capacity) {
				producersWaitingCondition.signal();
			}
			return queue.remove(0);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E poll() {
		try {
			return poll(0, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {

		}
		return null;
	}

	@Override
	public E remove() {
		mutex.lock();
		try {
			if (queue.isEmpty()) {
				throw new NoSuchElementException();
			}
			if (queue.size() == capacity) {
				producersWaitingCondition.signal();
			}
			return queue.remove(0);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		mutex.lock();
		try {
			return capacity - queue.size();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		mutex.lock();
		try {
			return queue.contains(o);
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}
}