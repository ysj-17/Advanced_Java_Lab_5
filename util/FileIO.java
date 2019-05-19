package util;


import java.io.*;
import java.util.Properties;
import java.util.StringTokenizer;
import exception.AutoException;
import model.Automobile;



public class FileIO {

			private String fname;
			private String s1 = "";
			private int container = 0;
			private int numOption = 0;
			private Automobile auto = new Automobile();
			
			public FileIO() {};
			
			public Automobile readAuto(String name) throws AutoException {
				fname = name;
				
				if(!new File(fname).exists())
					throw new AutoException(100); // Exception Thrown "Custom Error: Filename not found"
				else {
				
			
				try {
							FileReader file = new FileReader(fname);
							@SuppressWarnings("resource")
							BufferedReader buff = new BufferedReader(file);
			
							// Starting to read the Text File
							String line = buff.readLine();
							StringTokenizer st = new StringTokenizer(line,",");
							s1 = st.nextToken();
							
							container = Integer.parseInt(s1); // This will get how many OptionSets there are (Dynamic)
							auto.setNumOfOptionSets(container); // Related
							s1 = st.nextToken();
							auto.setAutoMake(s1);
							s1 = st.nextToken();
							auto.setAutoModel(s1); //System.out.println(s1);
							
							for (int index = 0; index < container-1; index++) {
								line = buff.readLine();
								st = new StringTokenizer(line,",");
								s1 = st.nextToken();
								
								if (s1.equals("Base Price")) { // To get base price
									if (st.hasMoreTokens() == true) {
									s1 = st.nextToken();
									auto.setBasePrice(Double.parseDouble(s1));
									} else {
										throw new AutoException(101); // Exception Thrown "Custom Error: Missing price for Automobile in input file"
									}
								}	
				
								// Get the next token
								if (index == 0) {
									line = buff.readLine();
									st = new StringTokenizer(line,",");
									s1 = st.nextToken();
								}
								
								if (st.hasMoreTokens() == true) 
								auto.setOptionSetNames(s1, index); // Get the name of OptionSet
								else
									throw new AutoException(102); // Exception Thrown "Custom Error: Missing data from OptionSet"
								numOption = Integer.parseInt(st.nextToken()); // Set the number of options
								auto.setNumOfOptions(index, numOption);// Get how many Options there are in the Option Set
								for (int j_index = 0; j_index < numOption; j_index++) {
									line = buff.readLine();
									st = new StringTokenizer(line,","); // Tokenize line to get the Options
									s1 = st.nextToken();
									if (st.hasMoreTokens() == true) 
										auto.setOneOptionName(index, j_index, s1); //String option_set = ""; // Set the name here need to use j_index
									else
										throw new AutoException(103); // Exception Thrown "Custom Error: Missing data from Option"
									s1 = st.nextToken();
									double price = Double.parseDouble(s1);
									auto.setOneOptionPrice(index, j_index, price);
								}		
							}
							
							buff.close();
					} catch (IOException e) {
					System.out.println("Error --" +e.toString());
					}
				return auto;
				}
			}
			
		public void cerealKiller (Automobile auto) {
			String filename = auto.getAutoModel();
			try {
				FileOutputStream fileOut = new FileOutputStream(filename+".ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(auto);
				out.close();
				fileOut.close();
				} catch (IOException e) {
					System.out.println("Error --" +e.toString());
				}
		}
		
		public Automobile getCereal (String name) throws ClassNotFoundException {
			Automobile newAuto = new Automobile();
			try {
				FileInputStream fileIn = new FileInputStream(name+".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				newAuto = (Automobile) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return newAuto;
			} catch (ClassNotFoundException c) {
				System.out.println("Automotive not found");
				c.printStackTrace();
				return newAuto;
			}
			return newAuto;
		}
		
		public Automobile readAutoFromPropFile (Object obj) {
			Properties props= new Properties();
			props = (Properties) obj;
			//System.out.println(props.getProperty("CarMake"));
			auto.setAutoMake(props.getProperty("CarMake"));
			auto.setAutoModel(props.getProperty("CarModel"));
			auto.setBasePrice(Double.parseDouble(props.getProperty("BasePrice")));
			System.out.println(Double.parseDouble(props.getProperty("BasePrice")));
			auto.setNumOfOptionSets(6);
			
			if(!props.getProperty("CarMake").equals(null))
			{
				auto.setOptionSetNames(props.getProperty("Option1"), 0); 
				auto.setNumOfOptions(0, 10);
				for (int index = 0; index < 10; ++index) {
					String holder = "Option1." +Integer.toString(index);
					auto.setOneOptionName(0, index, props.getProperty(holder));
					holder += "Price";
					auto.setOneOptionPrice(0, index, Double.parseDouble(props.getProperty(holder)));
				}
				auto.setOptionSetNames(props.getProperty("Option2"), 1);
				auto.setNumOfOptions(1, 2);
				for (int index = 0; index < 2; ++index) {
					String holder = "Option2." +Integer.toString(index);
					auto.setOneOptionName(1, index, props.getProperty(holder));
					holder += "Price";
					auto.setOneOptionPrice(1, index, Double.parseDouble(props.getProperty(holder)));
				}
				auto.setOptionSetNames(props.getProperty("Option3"), 2);
				auto.setNumOfOptions(2, 3);
				for (int index = 0; index < 3; ++index) {
					String holder = "Option3." +Integer.toString(index);
					auto.setOneOptionName(2, index, props.getProperty(holder));
					holder += "Price";
					auto.setOneOptionPrice(2, index, Double.parseDouble(props.getProperty(holder)));
				}
				auto.setOptionSetNames(props.getProperty("Option4"), 3);
				auto.setNumOfOptions(3,2);
				for (int index = 0; index < 2; ++index) {
					String holder = "Option4." +Integer.toString(index);
					auto.setOneOptionName(3, index, props.getProperty(holder));
					holder += "Price";
					auto.setOneOptionPrice(3, index, Double.parseDouble(props.getProperty(holder)));
				}
				auto.setOptionSetNames(props.getProperty("Option5"), 4);
				auto.setNumOfOptions(4, 2);
				for (int index = 0; index < 2; ++index) {
					String holder = "Option5." +Integer.toString(index);
					auto.setOneOptionName(4, index, props.getProperty(holder));
					holder += "Price";
					auto.setOneOptionPrice(4, index, Double.parseDouble(props.getProperty(holder)));
				}
			}		
			return auto;
		}
		
		public ObjectOutputStream AutoToCereal (Automobile auto) {
			ObjectOutputStream outStream = null;
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
			try {
				outStream = new ObjectOutputStream(byteOut);
				outStream.writeObject(auto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return outStream;
		}
		
		// NEW FUNCTION FOR LAB 5
		public Automobile readAutoFromStringBuff (Object obj) throws AutoException {
			StringTokenizer st = new StringTokenizer(obj.toString().trim(),"\n\r,");
			s1 = st.nextToken();
			container = Integer.parseInt(s1); // This will get how many OptionSets there are (Dynamic)
			auto.setNumOfOptionSets(container); // Related
			s1 = st.nextToken();
			auto.setAutoMake(s1);
			s1 = st.nextToken();
			auto.setAutoModel(s1); //System.out.println(s1);
			
			for (int index = 0; index < container-1; index++) {
				s1 = st.nextToken();
				//System.out.println(s1);
				
				if (s1.equals("Base Price")) { // To get base price
					if (st.hasMoreTokens() == true) {
					s1 = st.nextToken();
					auto.setBasePrice(Double.parseDouble(s1));
					} else {
						throw new AutoException(101); // Exception Thrown "Custom Error: Missing price for Automobile in input file"
					}
				}	

				// Get the next token
				if (index == 0) {
					s1 = st.nextToken();
				}
				
				if (st.hasMoreTokens() == true) 
				auto.setOptionSetNames(s1, index); // Get the name of OptionSet
				else
					throw new AutoException(102); // Exception Thrown "Custom Error: Missing data from OptionSet"
				numOption = Integer.parseInt(st.nextToken()); // Set the number of options
				auto.setNumOfOptions(index, numOption);// Get how many Options there are in the Option Set
				for (int j_index = 0; j_index < numOption; j_index++) {
					s1 = st.nextToken();
					if (st.hasMoreTokens() == true) 
						auto.setOneOptionName(index, j_index, s1); //String option_set = ""; // Set the name here need to use j_index
					else
						throw new AutoException(103); // Exception Thrown "Custom Error: Missing data from Option"
					s1 = st.nextToken();
					double price = Double.parseDouble(s1);
					auto.setOneOptionPrice(index, j_index, price);
				}		
			}
			return auto;
		}
}


			


