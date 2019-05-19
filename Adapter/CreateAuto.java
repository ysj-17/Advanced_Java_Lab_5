package Adapter;

import java.util.Properties;

// contract that these functions are the only way for the internal stuff to get to the outside world

public interface CreateAuto {
	public void buildAuto (String fileName);
	//public void buildAuto_prop_file (Properties obj);
	public void printAuto (String ModelName);
	public void printCustomAuto (String ModelName);
	public void printTotalPrice (String modelName);
}
