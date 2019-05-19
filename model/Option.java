package model;

import java.io.Serializable;

class Option implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nameOfOption;
	private double optionPrice;
	
	public Option(){}
	
	public Option (String name) {
		nameOfOption = name;
		optionPrice = 0;
	}
	
	public String getNameOfOption() {
		return nameOfOption;
	}
	public void setNameOfOption(String nameOfOption) {
		this.nameOfOption = nameOfOption;
	}
	public double getOptionPrice() {
		return optionPrice;
	}
	public void setOptionPrice(double optionPrice) {
		this.optionPrice = optionPrice;
	}
	public void printOptions() {
		System.out.print(nameOfOption +" Price: " +optionPrice);
	}
}
