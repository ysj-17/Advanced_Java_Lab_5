package Adapter;
import java.util.LinkedHashMap;
import java.util.Properties;

import exception.AutoException;
import model.*;
import scale.EditOptions;


public abstract class proxyAutomobile {
	private Automobile a1 = new Automobile();	
	static private LinkedHashMap<String, Automobile> container = new LinkedHashMap<String,Automobile>();
	
	public LinkedHashMap<String, Automobile> returnMap(){
		return container;
	}

	public void buildAuto (String fileName) {
		a1 = a1.createAutoFromFile(fileName);
		String hashKey = a1.getAutoMake() +" " +a1.getAutoModel();
		container.put(hashKey, a1); // To make Key --> combine both make and model
	}
	
	public void buildAuto_prop_file (Object obj) {
		a1 = a1.createAutoFromPropFile(obj);
		String hashKey = a1.getAutoMake() +" " +a1.getAutoModel();
		container.put(hashKey, a1); // To make Key --> combine both make and model
	}
	
	public void readAutoFromStringBuff (Object obj) {
		a1 = a1.readAutoFromStringBuff(obj);
		String hashKey = a1.getAutoMake() +" " +a1.getAutoModel();
		container.put(hashKey, a1); // To make Key --> combine both make and model
	}

	public void printAuto (String modelName) {
		try {
			if (container.containsKey(modelName)) {
				a1 = container.get(modelName);
				a1.print();
			}
			else 
				throw new AutoException();

		} catch (AutoException e) {
			e.getErrorMsgFromIndex(200);
			modelName = (e.fix(200).toString());
		}
	}
	
	public synchronized void updateOptionSetName (String modelName, String optionSetname, String newName) {
		
		try {
			if (container.containsKey(modelName)) {
				a1 = container.get(modelName);
				a1.changeSetOfOptionNames(optionSetname, newName);
			}
			else 
				throw new AutoException();
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(200);
		}
	}
	
	public void updateOptionPrice (String modelName, String optionName, String option, double newprice) {
		
		try {
			if (container.containsKey(modelName)) {
				a1 = container.get(modelName);
				a1.changePriceOfOption(optionName, option, newprice);
			}
			else {
				System.out.println("bad things in bad things in updateOptionPrice");
				throw new AutoException();
			}
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(200);
		}
	}
	

	public void getError (int errorNum) {
		AutoException fixAuto = new AutoException();
		String errormsg = fixAuto.fix(0);
		System.out.println(errormsg);
	}
	
	
// NEW FUCNTION FOR LAB 3 --> Print the custom auto you built (in CreateAuto Interface)
	public void printCustomAuto (String ModelName) {
		try {
			if (container.containsKey(ModelName)) {
				a1 = container.get(ModelName);
				a1.printCustomAuto();
			}
			else 
				throw new AutoException();

		} catch (AutoException e) {
			e.getErrorMsgFromIndex(200);
			ModelName = (e.fix(200).toString());
		}
	}
	
// NEW FUCNTION FOR LAB 3 --> Print the custom auto you built (in UpdateAuto Interface)
	public void updateChoice (String Modelname, String OptionSetname, String Optionname) {
		try {
			if (container.containsKey(Modelname)) {
				a1 = container.get(Modelname);
				a1.setOptionChoice(OptionSetname, Optionname);
			}
			else 
				throw new AutoException();
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(200);
		}
	}

// NEW FUCNTION FOR LAB 3 --> Print the PRICE of custom auto you built (in UpdateAuto Interface)

	public void printTotalPrice(String modelName) {
		double optionprice = 0.0;
		try {
			if (container.containsKey(modelName)) {
				a1 = container.get(modelName);
				optionprice = a1.getTotalPrice();
				System.out.println("\nOption Price: " +optionprice);
				optionprice += a1.getBasePrice();
				System.out.println("Total Price: " +optionprice +"\n");
			}
			else 
				throw new AutoException();
		} catch (AutoException e) {
			e.getErrorMsgFromIndex(200);
			modelName = (e.fix(200).toString());
		}
	}
	
	public void threadMethods(String[] autoDetails, int switchNum, int threadnum) {
		EditOptions operation = new EditOptions(this, autoDetails, switchNum, threadnum);
		operation.start();
	}

}














