package telran.multithreading.atomic;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
		IntStream.range(0, numberOfRacers)
		.forEach(i -> raceMenus.add(races.get(i).getRaceMenu()));
		raceMenus.add(Item.of("Start Races", MultipleRacesMenu::startRaces));
		raceMenus.add(Item.exit());
	}

	private static void fillInRaces() {
		IntStream.range(0, numberOfRacers)
		.forEach(i -> races.add(new RaceMenu()));
	}
	
	private static void startRaces(InputOutput io) {		
		for(int i = 0; i < numberOfRacers; i++) {
			System.out.println();	
			System.out.printf("#%d %s\n",i + 1, races.get(i).getRaceMenu().displayName());		
			System.out.println("place  racer number   time");			
			races.get(i).startRace(io);
		}
	}
}
