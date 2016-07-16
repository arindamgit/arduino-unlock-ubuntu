package serialcomms;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import gnu.io.CommPortIdentifier;

public class Main {
	Entry<String, CommPortIdentifier> arduinoPort = null;
	Set<Entry<String, CommPortIdentifier>> portsWithoutArduino = null;
	Set<Entry<String, CommPortIdentifier>> portsWithArduino = null;
	Communicator comm = new Communicator();
	private static int MAX_TRY = 15000;
	public static void main(String args[]) throws IOException, InterruptedException {
		instructions();
		Main thisClass = new Main();
		System.out.println("Unplug your Arduino... When done press Enter");
		System.in.read();
		thisClass.portsWithoutArduino = thisClass.getPorts();
		System.out.println("Now plug-in your Arduino... When done press Enter");
		System.in.read();
		int tryst = 0;
		System.out.println("Trying to connect to your Arduino, please stand by");
		do {
			tryst++;
			if(tryst % 1000 == 0)
				System.out.print(".");
			if(tryst > MAX_TRY) {
				System.out.println("\nCannot find your Arduino. Sorry");
				System.exit(-1);
			}
			thisClass.portsWithArduino = thisClass.getPorts();
		} while(thisClass.portsWithArduino.size() == thisClass.portsWithoutArduino.size());
		System.out.println();
		Iterator<Entry<String, CommPortIdentifier>> iterator = thisClass.portsWithArduino.iterator();
		while(iterator.hasNext()) {
			Entry<String, CommPortIdentifier> testPort = iterator.next();
			if(!thisClass.portsWithoutArduino.contains(testPort))
				thisClass.arduinoPort = testPort;
		}
		System.out.println("Arduino port found at " + thisClass.arduinoPort.getKey());
	}
	private Set<Entry<String, CommPortIdentifier>> getPorts() {
		return comm.searchForPorts().entrySet();
	}
	private static void instructions() {
		System.out.println("The program will try to detect your arduino");
		System.out.println("Please follow the instructions");
		System.out.println(" ----------------------------------------- ");
	}
}
