package model;

import java.io.Serializable;
import java.util.ArrayList;

import exception.AutoException;


class OptionSet implements Serializable {
		private static final long serialVersionUID = 1L;
		private ArrayList<Option> listOfOptions;
		private String optionSetName;
				
		protected OptionSet() {}
		
		public OptionSet buildCustomOption (Option custom, String setName) {
			OptionSet customOptionSet = new OptionSet();
			customOptionSet.optionSetName = setName;
			customOptionSet.setListOfOptions(1);
			customOptionSet.setListofOptionNames(0, custom.getNameOfOption());
			customOptionSet.setListOfOptionPrices(0, custom.getOptionPrice());
			return customOptionSet;
		}
		
		public ArrayList<Option> getListOfOptions() {
			return listOfOptions;
		}
	
		public String getOptionSetName() {
			return optionSetName;
		}
		
		public void setOptionSetName(String optionSetName) {
			this.optionSetName = optionSetName;
		}
		
		public void setListOfOptions(int numOfOptions) { // Set the number of options in the OptionSet
			listOfOptions = new ArrayList<Option>(numOfOptions-1);
			for (int index = 0; index <numOfOptions; index++)
				listOfOptions.add(new Option());
		}
		
		public void setListofOptionNames(int j_index, String name) { // Fix #1 from Lab 1
			listOfOptions.get(j_index).setNameOfOption(name);
		}
		
		public void setListOfOptionPrices(int j_index, double price) { // Fix #2 from Lab 1
			listOfOptions.get(j_index).setOptionPrice(price);
		}
		
		
		public void setPrice (int index, int price) {
			listOfOptions.get(index).setOptionPrice(price);
		}

//NEW FUCNTION FOR LAB 2 <-- Was made for changePriceOfCertainOption in Autmobile class
		public void updateCertinOptionPrice(String optionName, double newprice) throws AutoException { // Set exception here for NPE or out of bounds
			
			for (int index = 0; index < listOfOptions.size(); index++) { // Search is here too 
				if (listOfOptions.get(index).getNameOfOption().equals(optionName)) {
					listOfOptions.get(index).setOptionPrice(newprice);
					break;
				} else
					throw new AutoException(202);
			}
		}
		
//NEW FUNCTION FOR LAB 3 <-- Get particular chosen option name
		
		public String getOptionName() {
			return listOfOptions.get(0).getNameOfOption();
		}
		
//NEW FUNCTION FOR LAB 3 <-- Get particular chosen option price
		
		public double getOptionPrice() {
			return listOfOptions.get(0).getOptionPrice();
		}

//NEW FUNCTION FOR LAB 3 <-- Need to choose an option
		
		public Option chooseAnOption(String optionName) throws AutoException {
			for (int index = 0; index < listOfOptions.size(); index++) { // Search is here too 
				if (listOfOptions.get(index).getNameOfOption().equals(optionName)) {
					return listOfOptions.get(index);
					//Collections.swap(listOfOptions, 0,index);
					//return;
				}
			}
			
			throw new AutoException(202);
		}
}

