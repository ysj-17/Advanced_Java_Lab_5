package Adapter;

public interface UpdateAuto {
	public void updateOptionSetName (String Modelname, String OptionSetname, String newName);
	public void updateOptionPrice (String Modelname, String Optionname, String option, double newprice);
	public void updateChoice (String Modelname, String OptionSetname, String Optionname);
}
