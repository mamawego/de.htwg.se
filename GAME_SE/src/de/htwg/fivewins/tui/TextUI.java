package de.htwg.fivewins.tui;

import java.util.Scanner;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.IObserver;

public class TextUI implements IObserver{
	
	private FiveWinsController controller;
	private Scanner scanner;
	

	public TextUI(FiveWinsController controller){
		this.controller = controller;
		controller.addObserver(this);
		scanner = new Scanner (System.in);	
	}

	@Override
	public void update() {
		printTUI();
	}

	public boolean iterate() {
		return handleInputOrQuit(scanner.next());
	}

	public void printTUI() {
		System.out.print("\n" + controller.getFieldString() + "\n");
		System.out.print(controller.getStatus() + "\n");
		System.out.print("\n");
		System.out.print("Please enter a command( q - quit, u - update, n - new, x,y - set cell(x,y)):\n"); // 1-15 - set size
	}
	
	public boolean handleInputOrQuit(String line) {
		boolean quit=false;
		if (line.equalsIgnoreCase("q")) {
			quit=true;
		}
		if (line.equalsIgnoreCase("u")) {
			//Do nothing, just redraw the updated grid
			update();
		}
		if (line.equalsIgnoreCase("n")) {
			//Restart game
			new TextUI(new FiveWinsController(new Field(3)));
		}
		// if the command line has the form 12, set the cell (1,2) to value 3
		if (line.matches("[0-9]{1,2}?,[0-9]{1,2}?")){
			String[] numbers = line.split(",");
			int arg0 = Integer.parseInt(numbers[0]);
			int arg1 = Integer.parseInt(numbers[1]);
			boolean successfulFieldChange = controller.setValue(arg0, arg1, controller.getPlayerSign());
			if(successfulFieldChange) {
				System.out.print("Der Gewinner ist " + controller.winRequest() + "\n");
				controller.countTurn();
			}
		}

		return quit;
	}

	
	

}
