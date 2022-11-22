package telran.multithreading;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RaceMenu {
	
	private static final int MIN_NUMBER_OF_RACERS = 2;
	private static final int MAX_NUMBER_OF_RASERS = 20;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	private int numberOfRacers;
	private int distance;
	List<Racer> resultsTable;

	public RaceMenu() {
		this.numberOfRacers = MIN_NUMBER_OF_RACERS;
		this.distance = MIN_DISTANCE;
	}
	
	public Menu getRaceMenu() {
		return new Menu("Race with " + numberOfRacers
				+ " riders for a distance of " + distance, 30, getItems());
	}
	
	private Item[] getItems() {
		Item[] res = {
			Item.of("Start the race", io -> {
				System.out.println("\nplace\tracer number\ttime");
				startRace(io);	
			}),
			Item.of("Change the number of racers", this::changeNumberOfRacers, true),
			Item.of("Change the distance", this::changeDistance, true),
			Item.of("Exit", io -> {
				if(!MultipleRacesMenu.races.isEmpty()) {
					MultipleRacesMenu.raceMenus.clear();
					MultipleRacesMenu.fillInRacesMenus();
				}
			}, true)			
		};
		return res;
	}
	
	private void changeNumberOfRacers(InputOutput io) {
		numberOfRacers  = io.readInt("enter number of racers (" + MIN_NUMBER_OF_RACERS
				+ " - " + MAX_NUMBER_OF_RASERS + ")", "no number",
				MIN_NUMBER_OF_RACERS, MAX_NUMBER_OF_RASERS);
		getRaceMenu().perform(new ConsoleInputOutput());
	}
	
	private void changeDistance(InputOutput io) {
		distance  = io.readInt("enter distance (" + MIN_DISTANCE 
				+ " - " + MAX_DISTANCE + ")", "no number",
				MIN_DISTANCE, MAX_DISTANCE);
		getRaceMenu().perform(new ConsoleInputOutput());
	}
	
	public void startRace(InputOutput io) {
		Racer[] racers = new Racer[numberOfRacers];
		resultsTable = new ArrayList<Racer>();
		Racer.distance = distance;
		Racer.startTime = Instant.now();
		startingRacers(racers);
		joiningRacers(racers);
		printingResultTable();
	}

	private void startingRacers(Racer[] racers) {
		IntStream.range(0, numberOfRacers).forEach(i -> {
			racers[i] = new Racer(i + 1, this);
			racers[i].start();
		});
	}

	private void joiningRacers(Racer[] racers) {
		Arrays.stream(racers).forEach(racer -> {
			try {
				racer.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private void printingResultTable() {
		IntStream.range(0, resultsTable.size())
		.mapToObj(i -> toPrintedString(i))
		.forEach(System.out::println);
		
	}
	
	private String toPrintedString(int index) {
		Racer racer = resultsTable.get(index);
		return String.format("%3d\t%7d\t\t%d", 
				index + 1, racer.getRacerNumber(), racer.getRunningTime());
	}
}