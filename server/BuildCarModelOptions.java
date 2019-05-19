package server;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import Adapter.*;
import model.Automobile;
import util.FileIO;

public class BuildCarModelOptions extends proxyAutomobile {

	////////// PROPERTIES //////////

	private static final int WAITING = 0;
	private static final int REQUEST_BUILD_AUTO = 1;
	private static final int REQUEST_CONFIGURE_AUTO = 2;

	private int state = WAITING;
	//private Automobile buildAuto = new Automobile();

	////////// CONSTRUCTORS //////////

	public BuildCarModelOptions() {

	}

	////////// INSTANCE METHODS //////////

	public Object processRequest(Object obj) {
		Object toClient = null;

		if (state == REQUEST_BUILD_AUTO) { // will get a either a prop or txt file name here... need to modify FileIO and createAuto
			//add code to buildauto
			
			if (obj instanceof StringBuffer) {
				readAutoFromStringBuff(obj);
			} else {
				buildAuto_prop_file(obj);
			}
			
			toClient = "Automobile object successfully added to database\n"
					+ "Press any key to return to main menu";
			
		}
		else if (state == REQUEST_CONFIGURE_AUTO) {
			LinkedHashMap<String, Automobile> map = returnMap();
			toClient = map.get(obj);
		}
		else {
			System.out.println("Error in processRequest");
		}

		this.state = WAITING;

		return toClient;
	}

	public String setRequest(int i) {
		String output = null;

		if (i == 1) {
			this.state = REQUEST_BUILD_AUTO;
			output = "Upload a file to create an Automobile";
		}
		else if (i == 2) {
			this.state = REQUEST_CONFIGURE_AUTO;
			//add code for configureauto
			LinkedHashMap<String, Automobile> map = returnMap();
			output = "Select an Automobile from the following list to configure: \n";
			for (String variableName : map.keySet())
	        {
	            String variableKey = variableName;
	            output += "Name: " + variableKey +"\n";
	        }

		}
		else {
			output = "Invalid request";
		}

		return output;
	}


}