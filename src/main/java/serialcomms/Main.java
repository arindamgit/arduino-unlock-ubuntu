package serialcomms;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import gnu.io.CommPortIdentifier;

public class Main {
	public static void main(String args[]) {
		Communicator comm = new Communicator();
		Set<Entry<String, CommPortIdentifier>> ports = comm.searchForPorts().entrySet();
		Iterator<Entry<String, CommPortIdentifier>> portIterator = ports.iterator();
		while(portIterator.hasNext()) {
			Entry<String, CommPortIdentifier> port = portIterator.next();
			System.out.println(port.getKey());
		}
	}
	
}
