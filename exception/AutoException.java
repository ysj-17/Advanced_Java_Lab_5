package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap; 

import model.Automobile;


public class AutoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	HashMap<Integer, String> errorMap = new HashMap<Integer, String>();
	private String errorMsg = "";
	
	public AutoException () {
		super(); // refer to parent class obj
		createErrorMap(); // helper
	}

	public void createErrorMap() {
		
		errorMap.put(100, "Custom Error: Filename not found");
		errorMap.put(101, "Custom Error: Missing price for Automobile in input file");
		errorMap.put(102, "Custom Error: Missing data from OptionSet");
		errorMap.put(103, "Custom Error: Missing data from Option");
		
		errorMap.put(200, "Custom Error: Requested Autoname cannot be found");
		errorMap.put(201, "Custom Error: Requested Option Set Name cannot be found");
		errorMap.put(202, "Custom Error: Requested Option cannot be found");
		
		errorMap.put(300, "Custom Error: Requested Automobile not in Database");
		errorMap.put(301, "Custom Error: Requested Option Set not in Database");
		errorMap.put(303, "Custom Error: Requested Option not in Database");
		errorMap.put(304, "Custom Error: Too many Custom Options requested");
		
	}
	
	public AutoException(int thrownErrorNum) {
		super();
		createErrorMap();
		errorMsg = errorMap.get(thrownErrorNum);
		writeLog(errorMsg);
	}
	
	public void getErrorMsg() {
		System.out.println(errorMsg);
	}
	
	public void getErrorMsgFromIndex(int thrownErrorNum) {
		errorMsg = errorMap.get(thrownErrorNum);
		System.out.println(errorMsg);
		writeLog(errorMsg);
	}
	
	public String fix(int errno) {
		
		String fixit = "";
		Fix100to103 f1 = new Fix100to103();
		Fix200to202 f2 = new Fix200to202();
		Fix300to303 f3 = new Fix300to303();
		
		switch(errno) {
		
		case 0: fixit = "Acces for FixAuto";
		
		case 100: fixit = f1.fix100(errno);break;
		case 101: fixit = f1.fix101(errno);break;
		case 102: fixit = f1.fix102(errno);break;
		case 103: fixit = f1.fix103(errno);break;
		
		case 200: fixit = f2.fix200(errno);break;
		case 201: fixit = f2.fix201(errno);break;
		case 202: fixit = f2.fix202(errno);break;

		case 300: fixit = f3.fix300(errno);break;
		case 301: fixit = f3.fix301(errno);break;
		case 302: fixit = f3.fix302(errno);break;
		case 303: fixit = f3.fix303(errno);break;

		}
		
		return fixit;
	}
	
	public void writeLog (String errlog) {
		try {
			DateFormat d1 = new SimpleDateFormat("MM/DD/yy HH:mm:ss");
			Date date = new Date();
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("errlog.txt", true)));
			writer.println("[" + d1.format(date) + "] " + errlog);
			writer.close();
		} catch (IOException e) {
			System.out.println("IO Error, try restarating the process");
			System.exit(1);
		} finally {
		}
	}
	

}
