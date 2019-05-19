package scale;

import Adapter.proxyAutomobile;

public class EditOptions extends proxyAutomobile implements Runnable {
	private Thread t;
	private int threadno = 1;
	private int switchNum;
	private String[] autoDetails;
	private proxyAutomobile proxy;
	
	public EditOptions(proxyAutomobile proxy, String[] autoDetails, int switchNum, int threadnum) {
		this.proxy = proxy;
		t = new Thread(this);
		this.autoDetails = autoDetails;
		this.switchNum = switchNum;
		threadno = threadnum;
	}
	
	public void run() {
		synchronized(proxy) {
		System.out.println("Starting thread " + threadno + " ... ");
		System.out.println("Thread " + threadno + " executing operation " + switchNum + " ... ");
		switch (switchNum) {
		case 0: 
			proxy.updateOptionSetName(autoDetails[0],autoDetails[1], autoDetails[2]);
			break;
		case 1: 
			proxy.updateOptionPrice(autoDetails[0], autoDetails[1], autoDetails[2], Double.parseDouble(autoDetails[3]));
			break;
		default: 
			System.out.println("Operationg number (switch number) is not found");
		}
		
		System.out.println("Thread " + threadno + " finishing operation " + switchNum + " ... ");
		System.out.println("Stopping thread " + threadno + " ... \n");
		
		}
		try {
			t.join();
		} catch (Exception e) {
			System.out.println("Thread Interrupted");
		}
		//t.interrupt();
		return;
	}
	
	public void start() {
		t.start();
	}
	
}