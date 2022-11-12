package telran.multithreading.test;

import telran.multithreading.ConsoleInputOutput;
import telran.multithreading.Menu;
import telran.multithreading.RaceMenu;

public class ThreadsRaceApp {	

	public static void main(String[] args) {
		Menu menu = RaceMenu.getRaceMenu();
		menu.perform(new ConsoleInputOutput());		
	}

}