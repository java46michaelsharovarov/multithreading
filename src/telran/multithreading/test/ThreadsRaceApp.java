package telran.multithreading.test;

import telran.multithreading.ConsoleInputOutput;
import telran.multithreading.Item;
import telran.multithreading.Menu;
import telran.multithreading.MultipleRacesMenu;
import telran.multithreading.RaceMenu;

public class ThreadsRaceApp {	

	public static void main(String[] args) {
		Menu menu = new Menu ("Race Menu", 3,
				Item.of("One Race", io -> new RaceMenu().getRaceMenu().perform(io)),
				Item.of("Multiple Races", MultipleRacesMenu::setNumberOfRaces),
				Item.exit());
		menu.perform(new ConsoleInputOutput());		
	}

}