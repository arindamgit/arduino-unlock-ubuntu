package serialcomms;

import java.util.Enumeration;
import java.util.HashMap;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Communicator implements SerialPortEventListener {
	
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub		
	}
	
	public HashMap<String, CommPortIdentifier> searchForPorts() {
		//map the port names to CommPortIdentifiers
		HashMap<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();
		//for containing the ports that will be found
	    Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
			//get only serial ports
			if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portMap.put(curPort.getName(), curPort);
			}
		}
		return portMap;
	}
}
