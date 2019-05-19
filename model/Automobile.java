package model;
import java.io.Serializable;
import exception.AutoException;
import util.FileIO;
import java.util.ArrayList;
import java.util.Properties;

// I found it better to put synchornized methods in Auto class since that is where all the traffic/queue happens
// Putting it in the class that contains the CRUD operations wouldn't affect the threads too much
// I feel that the CRUD operations is the "highway" of the program and doesn't need the "traffic laws"

public class Automobile implements Serializable {
	private static final long serialVersionUID = 1L;
	private double basePrice;
	private ArrayList<OptionSet> optionSets;
	private ArrayList<OptionSet> customOptions;
	
	// New for Lab 3
	
	private String autoModel;
	private String autoMake;
	
	public String getAutoModel() {
		return autoModel;
	}

	public void setAutoModel(String autoModel) {
		this.autoModel = autoModel;
	}

	public String getAutoMake() {
		return autoMake;
	}

	public void setAutoMake(String autoMake) {
		this.autoMake = autoMake;
	}
	

	public Automobile () {};
	
	public Automobile createAutoFromFile (String fileName) {
		FileIO fileInput = new FileIO();
		Automobile container = new Automobile();
		try {
			container = fileInput.readAuto(fileName);
		} catch (AutoException e) {
			e. getErrorMsg();
		}
		return container;
	};
	
	public Automobile createAutoFromPropFile (Object obj) {
		FileIO fileInput = new FileIO();
		Automobile container = new Automobile();
		container = fileInput.readAutoFromPropFile(obj);
		return container;
	}
	
	public Automobile readAutoFromStringBuff (Object obj) {
		FileIO fileInput = new FileIO();
		Automobile container = new Automobile();
		try {
			container = fileInput.readAutoFromStringBuff(obj);
		} catch (AutoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return container;
	}
	
	// fixing here
	public void setNumOfOptionSets (int numOfOptionSets) {
		optionSets = new ArrayList<OptionSet>(numOfOptionSets-1);
		customOptions = new ArrayList<OptionSet>(numOfOptionSets-1);
		for (int index = 0; index < numOfOptionSets-1; index++) {
			optionSets.add(new OptionSet());
		}
	}


	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	
// Need set option name and price seperate functions
	
	public void setOptionSetNames(String name, int index) { // Fix Not Needed deals with OptionSet
		optionSets.get(index).setOptionSetName(name);
	}
	
	public void setNumOfOptions (int index, int numOfOptions) { // Fix not needed deals with OptionSet
		optionSets.get(index).setListOfOptions(numOfOptions);
	}
	
	public void setOneOptionName(int index, int j_index, String name) { // Set Option Name //Fix here #1
		optionSets.get(index).setListofOptionNames(j_index, name);
	}
	
	public void setOneOptionPrice(int index, int j_index, double price) { // Fix here #2
		optionSets.get(index).setListOfOptionPrices(j_index,price);
	}
	
// NEW FUCNTION FOR LAB 2
	
	public void changeSetOfOptionNames(String optionSetname, String newName) { // has a search
		int index = -1;
		try {
			index = linearSearch(optionSetname);
			if (index > -1)
				optionSets.get(index).setOptionSetName(newName);
			else
				System.out.println("Option Set name bad");
		} catch (AutoException e) {
			// TODO Auto-generated catch block
			e.getErrorMsgFromIndex(201); // Exception Thrown "Custom Error: Requested Option Set Name cannot be found"
		} 
	}

// NEW FUCNTION FOR LAB 2 <-- has a corresponding function in OptionSet Class
	
	public void changePriceOfOption(String optionName, String option, double newprice) { // search here
		int index = -1;
		try {
			index = linearSearch(optionName);
			if (index > -1) {
				try {
				optionSets.get(index).updateCertinOptionPrice(option, newprice);
				} catch (AutoException e) {
					e.getErrorMsgFromIndex(202);
				}
			}
			else
				System.out.println("changePriceOfOption name bad");
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(201); // Exception Thrown "Custom Error: Requested Option Set Name cannot be found"
		}
	}
	
	public void print() {
		System.out.println("Name of Auto: " +autoMake +" " +autoModel +"\nBase Price: " +basePrice +"\nNumber of Options: " +optionSets.size());
		System.out.print("Options Sets:\n\n");
		for (int index = 0; index < (optionSets.size()); index++) {
			System.out.print("  #" +(index+1) +". " +optionSets.get(index).getOptionSetName() +"\n");
			for (int secondIndex = 0; secondIndex < optionSets.get(index).getListOfOptions().size(); secondIndex++) {
				System.out.print("\t- ");
				optionSets.get(index).getListOfOptions().get(secondIndex).printOptions();
				//optionSets.get(index).listOfOptions.get(secondIndex).printOptions();
				System.out.print("\n");
			}
		}
	}
	
	public int linearSearch(String name) throws AutoException{
		for (int index = 0; index < optionSets.size(); index++) {
			if(optionSets.get(index).getOptionSetName().equals(name)) // there is a search here
				return index;
		}

		throw new AutoException();
	}
	
// NEW FUCNTION FOR LAB 3 <-- set to get chosen option name
	public String getOptionChoice(String setName) {
		int index = -1;
		try {
			index = linearSearch(setName);
			if (index > -1) 
				return optionSets.get(index).getOptionName();
			else
				System.out.println("Option Set name bad");
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(201); // Exception Thrown "Custom Error: Requested Option Set Name cannot be found"
		}
		return "Option Set name bad";
	}
	
// NEW FUCNTION FOR LAB 3 <-- set to get chosen option name
	public double getOptionChoicePrice(String setName) {
		int index = -1;
		try {
			index = linearSearch(setName);
			if (index > -1) 
				return optionSets.get(index).getOptionPrice();
			else
				System.out.println("Option Set name bad");
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(201); // Exception Thrown "Custom Error: Requested Option Set Name cannot be found"
		}
		return -1;
	}
	
// NEW FUCNTION FOR LAB 3 <-- set to option choice // Updated need to put new errors for too many customOptions
	public void setOptionChoice(String setName, String optionName) {
		int index = -1;
		try {
			index = linearSearch(setName);
			if (index > -1) {
				try {
				OptionSet container = new OptionSet();
				container = container.buildCustomOption(optionSets.get(index).chooseAnOption(optionName), setName);
				customOptions.add(container); // need to choose option
				if (customOptions.size() > optionSets.size())
					throw new AutoException(304); //more to add HERE
				} catch (AutoException e) {
					e.getErrorMsgFromIndex(202); // won't print message for 304
				}
			}
			else
				System.out.println("changePriceOfOption name bad");
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(201); // Exception Thrown "Custom Error: Requested Option Set Name cannot be found"
		}
	}

// NEW FUCNTION FOR LAB 3 <-- Print chosen Options // Updated need to put new errors for too many customOptions
	public void printCustomAuto() {
		System.out.println("\nName of Auto: " +autoMake +" " +autoModel +"\nBase Price: " +basePrice +"\nNumber of Custom Options: " +customOptions.size());
		System.out.print((optionSets.size() - customOptions.size()) +" more Custom Options may be chosen\n");
		System.out.print("Options Sets:\n\n");
		for (int index = 0; index < (customOptions.size()); index++) {
			System.out.print("  #" +(index+1) +". " +customOptions.get(index).getOptionSetName() +"\n");
			System.out.print("\t- ");
			customOptions.get(index).getListOfOptions().get(0).printOptions();
			System.out.print("\n\n");
		}
	}
	
// NEW FUCNTION FOR LAB 3 <-- Get total price of chosen options
	public double getTotalPrice() {
		double totalprice = 0.0;
		for (int index = 0; index < (customOptions.size()); index++) {
			totalprice += customOptions.get(index).getOptionPrice();
		}
		
		return totalprice;
	}
	
}
