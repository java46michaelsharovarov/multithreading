package telran.multithreading;

public class RaceMenu {
	
	private static final int MIN_NUMBER_OF_RACERS = 3;
	private static final int MAX_NUMBER_OF_RASERS = 10;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	int winnerNumber;
	private int numberOfRacers;
	private int distance;

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
				winnerNumber = 0;
				startRace(io);				
				System.out.printf("Congratulations to racer #%d (winner)!!!\n", winnerNumber);
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
		numberOfRacers  = io.readInt("enter number of racers (3 - 10)", "no number",
				MIN_NUMBER_OF_RACERS, MAX_NUMBER_OF_RASERS);
		getRaceMenu().perform(new ConsoleInputOutput());
	}
	
	private void changeDistance(InputOutput io) {
		distance  = io.readInt("enter distance (100 - 3500)", "no number",
				MIN_DISTANCE, MAX_DISTANCE);
		getRaceMenu().perform(new ConsoleInputOutput());
	}
	
	public void startRace(InputOutput io) {
		Racer[] racers = new Racer[numberOfRacers];
		for(int i = 0; i < numberOfRacers; i++) {
			racers[i] = new Racer(this, distance, i + 1);
			racers[i].start();
		}
		for(Racer racer : racers) {
			try {
				racer.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}