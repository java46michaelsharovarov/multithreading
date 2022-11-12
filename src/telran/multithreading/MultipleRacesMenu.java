package telran.multithreading;

import java.util.ArrayList;

public class MultipleRacesMenu {	
	
	public static ArrayList<RaceMenu> races = new ArrayList<>();
	public static ArrayList<Item> raceMenus = new ArrayList<>();
	private static int numberOfRacers;

	public static void setNumberOfRaces(InputOutput io) {
		numberOfRacers  = io.readInt("enter number of races", "no number");		
		fillInRaces();
		fillInRacesMenus();		
		Menu menu = new Menu("Multiple Races Menu", 4, raceMenus);
		menu.perform(new ConsoleInputOutput());
		races.clear();
		raceMenus.clear();
	}

	public static void fillInRacesMenus() {
		for(int i = 0; i < numberOfRacers; i++) {
			raceMenus.add(races.get(i).getRaceMenu());
		}
		raceMenus.add(Item.of("Start Races", MultipleRacesMenu::startRaces));
		raceMenus.add(Item.exit());
	}

	private static void fillInRaces() {
		for(int i = 0; i < numberOfRacers; i++) {
			races.add(new RaceMenu());
		}
	}
	
	private static void startRaces(InputOutput io) {		
		for(int i = 0; i < numberOfRacers; i++) {
			races.get(i).winnerNumber = 0;
			races.get(i).startRace(io);
		}
		for(int i = 0; i < numberOfRacers; i++) {
			System.out.printf("In race %d. Congratulations to racer #%d (winner)!!!\n",
					i + 1, races.get(i).winnerNumber);
		}
	}
}
