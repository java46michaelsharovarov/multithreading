package telran.multithreading;

public class RaceMenu {
	
	private static final int MIN_NUMBER_OF_RACERS = 3;
	private static final int MAX_NUMBER_OF_RASERS = 10;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	static int winnerNumber;
	private static Integer numberOfRacers = MIN_NUMBER_OF_RACERS;
	private static Integer distance = MIN_DISTANCE;
	
	public static Menu getRaceMenu() {
		return new Menu("Race with " + numberOfRacers
				+ " riders for a distance of " + distance, 30, getItems());
	}
	
	private static Item[] getItems() {
		Item[] res = {
			Item.of("Start the race", RaceMenu::StartRace),
			Item.of("Change the number of racers", RaceMenu::СhangeNumberOfRacers, true),
			Item.of("Change the distance", RaceMenu::СhangeDistance, true),
			Item.exit()			
		};
		return res;
	}
	
	private static void СhangeNumberOfRacers(InputOutput io) {
		numberOfRacers  = io.readInt("enter number of racers (3 - 10)", "no number",
				MIN_NUMBER_OF_RACERS, MAX_NUMBER_OF_RASERS);
		getRaceMenu().perform(new ConsoleInputOutput());
	}
	
	private static void СhangeDistance(InputOutput io) {
		distance  = io.readInt("enter distance (100 - 3500)", "no number",
				MIN_DISTANCE, MAX_DISTANCE);
		getRaceMenu().perform(new ConsoleInputOutput());
	}
	
	private static void StartRace(InputOutput io) {
		Racer[] racers = new Racer[numberOfRacers];
		for(int i = 0; i < numberOfRacers; i++) {
			racers[i] = new Racer(distance, i + 1);
			racers[i].start();
		}
		for(Racer racer : racers) {
			try {
				racer.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		System.out.printf("Congratulations to racer #%d (winner)\n", winnerNumber);
		winnerNumber = 0;
	}
}
