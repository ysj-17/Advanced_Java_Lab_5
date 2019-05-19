
package client;

import model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class SelectCarOptions {

	////////// PROPERTIES //////////
	private Scanner bin = new Scanner(System.in);

	////////// CONSTRUCTORS //////////

	public SelectCarOptions() {

	}

	////////// INSTANCE METHODS //////////

	public void configureAuto(Object obj, ObjectInputStream in) {
		//try {
			boolean done = false;
			Automobile a1 = (Automobile) obj;
			while (!done) {
				a1.print();
				System.out.println("Which OptionSet do you wish to select?");
				String optionset = bin.nextLine();
				System.out.println("Which Option do you want to choose?");
				String one_option = bin.nextLine();
				a1.setOptionChoice(optionset, one_option);
				System.out.println("Enter \"Done\" when finished or press any key to continue...");
				String exit = bin.nextLine();
				if(exit.equals("Done"))
					done = true;
			}
			
			a1.printCustomAuto();
			double price = a1.getTotalPrice() + a1.getBasePrice();
			System.out.println("Total Price: " +price);

	}
		
	public boolean isAutomobile(Object obj) {
		boolean isAutomobile = false;

		try {
			Automobile a1 = (Automobile) obj;
			isAutomobile = true;
		}
		catch (ClassCastException e) {
			isAutomobile = false;
		}

		return isAutomobile;
	}

}
