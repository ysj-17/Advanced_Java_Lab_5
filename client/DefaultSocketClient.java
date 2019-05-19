
package client;

import java.net.*;
import java.util.Properties;

import model.Automobile;

import java.io.*;

public class DefaultSocketClient extends Thread {

	////////// PROPERTIES //////////

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BufferedReader stdIn;
	private Socket sock;
	private String strHost;
	private int iPort;
	private CarModelOptionsIO clientFTP;
	private SelectCarOptions clientProtocol;

	////////// CONSTRUCTORS //////////

	public DefaultSocketClient(String strHost, int iPort) {
		this.strHost = strHost;
		this.iPort = iPort;
		
	}

	////////// INSTANCE METHODS //////////

	public void establishConnection() {
		try {
			 
			System.out.println("Connecting to host ... ");
			this.sock = new Socket(strHost, iPort);

			 
			System.out.println("Connected to host, creating object streams ... ");
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			stdIn = new BufferedReader(new InputStreamReader (System.in));

			clientFTP = new CarModelOptionsIO();
			clientProtocol = new SelectCarOptions();
		}
		catch (IOException e) {
			System.err.println("Error obtaining I/O for connection to host ... ");
			System.exit(1);
		}
	}

	public void handleConnection() {
		Object fromServer, toServer = null;

		try {
			
				System.out.println("Communicating with host ... ");

			while ((fromServer = in.readObject()) != null) {
				
				System.out.println("\nReceived server response ... ");
				System.out.println(fromServer.toString() +"\n");

				if (clientProtocol.isAutomobile(fromServer))
					clientProtocol.configureAuto(fromServer, in);

				System.out.println("Response to server: ");
				toServer = stdIn.readLine();
				if (toServer.toString().contains(".properties")) {
					toServer = clientFTP.loadPropsFile(toServer.toString());
				} else if (toServer.toString().contains(".txt")) {
					toServer = clientFTP.loadTextFile(toServer.toString());
				} else if (toServer.equals("0")) {
					break;
				}
				
				System.out.println("\nSending to server ... ");
				sendOutput(toServer);

			}

			
				System.out.println("Closing client input stream ... ");
			in.close();

		}
		catch (ClassNotFoundException e) {
			System.err.println("Error in downloaded object, object may be corrupted ... ");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Error in I/O stream ... ");
			System.exit(1);
		}

	}


	public void sendOutput(Object obj) {
		try {
			out.writeObject(obj);
		}
		catch (IOException e) {
			System.err.println("Error in I/O stream while sending object to host ... ");
			System.exit(1);
		}
	}

	@Override
	public void run() {
		establishConnection();
		handleConnection();
		try {
			 
			System.out.println("Closing client output stream ... ");
			out.close();

			 
			System.out.println("Closing client console input stream ... ");
			stdIn.close();

			System.out.println("Closing client socket ... ");
			sock.close();
		}
		catch (IOException e) {
			System.err.println("Error ending client connection ... ");
			System.exit(1);
		}
	}
	
	public static void main(String arg[]) {
		String strLocalHost = "";
		try {
			strLocalHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.err.println("Unable to find local host");
		}
		DefaultSocketClient d = new DefaultSocketClient(strLocalHost, 1234);
		d.start();

	}

}
