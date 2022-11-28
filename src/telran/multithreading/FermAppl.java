package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class FermAppl {

	private static final int N_TRUCKS = 4;
	private static final int N_LOADS = 5_000_000;

	public static void main(String[] args) {
		Truck[] trucks = new Truck[N_TRUCKS];
		while (true) {
			int input = userInput();
			Instant start = Instant.now();
			if(input == 3) {
				break;
			} 
		    System.out.println("loading...");			
			startTrucks(trucks, input);
			waitigForFinishing(trucks);
			printingResults(start);
		}		
	}
	
	private static void printingResults(Instant start) {
		System.out.printf("Report: elevator1 contains %d tons; elevator2 contains %d tons"
				+ "\nrunning time is %d\n", Truck.getElevator1(),
				Truck.getElevator2(), ChronoUnit.MILLIS.between(start, Instant.now()));
		System.out.printf("Waiting iteration counter: %d\n\n",
				Truck.getWaitingIterationCounter());
	}

	private static int userInput() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
	    System.out.println("1. Both elevators have the same lock object\n"
	    		+ "2. Elevators have different blocking objects\n"
	    		+ "3. Exit\n"
	    		+ "enter number of item");
	    while (true) {
			String input;
			try {
				input = scanner.nextLine();
				int res = Integer.parseInt(input);
				if (res >= 1 && res <= 3) {
				    return res;
				} 
				throw new Exception();
			} catch (Exception e) {
				System.out.println("enter number of item");
			}			
		}
	}

	private static void waitigForFinishing(Truck[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				
			}
		});		
	}

	private static void startTrucks(Truck[] trucks, int input) {
		Truck.elevator1 = 0;
		Truck.elevator2 = 0;
		Truck.waitingIterationCounter = new AtomicLong(0);
		if (input == 1) {
			startTruckWithOneMutex(trucks);
		} else if (input == 2) {
			startTruckWithTwoMutex(trucks);
		}
	}

	private static void startTruckWithOneMutex(Truck[] trucks) {
		IntStream.range(0, trucks.length)
		.forEach(i -> {
			trucks[i] = new TruckWithOneMutex(1, N_LOADS);
			trucks[i].start();
		});
	}
	
	private static void startTruckWithTwoMutex(Truck[] trucks) {
		IntStream.range(0, trucks.length)
		.forEach(i -> {
			trucks[i] = new TruckWithTwoMutexs(1, N_LOADS);
			trucks[i].start();
		});
	}

}