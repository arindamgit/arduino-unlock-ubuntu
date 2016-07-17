package serialcomms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Communicator implements SerialPortEventListener {
	final static int TIMEOUT = 2000;
	final static int NEW_LINE_ASCII = 10;
	
	InputStream input = null;
	OutputStream output = null;
	
	SerialPort serialPort = null;
	StringBuffer data = new StringBuffer();
	
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				byte singleData = (byte)input.read();
				if (singleData != NEW_LINE_ASCII) {
					String inputData = new String(new byte[] {singleData});
					data.append(inputData);
                }
				else {
					System.out.println(data.toString());
					data = new StringBuffer();
				}
            }
            catch (Exception e)
            {
            	System.out.println("Failed to read input data");
            }
        }
	}

	@SuppressWarnings("rawtypes")
	public HashMap<String, CommPortIdentifier> searchForPorts() {
		// map the port names to CommPortIdentifiers
		HashMap<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();
		// for containing the ports that will be found
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
			// get only serial ports
			if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portMap.put(curPort.getName(), curPort);
			}
		}
		return portMap;
	}

	public void connect(CommPortIdentifier portIdentifier) throws IOException {
		CommPort commPort;
		try {
			commPort = portIdentifier.open("serialcomms", TIMEOUT);
			serialPort = (SerialPort) commPort;
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();
		} catch (PortInUseException e) {
			System.out.println("PortInUseException has ocurred " + e.getMessage());
			e.printStackTrace();
			throw new IOException(e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException has ocurred " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	public void initListener() throws IOException {
		try {
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (TooManyListenersException e) {
			System.out.println("TooManyListenersException has ocurred " + e.getMessage());
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}

    }
	
	public void shutdown() throws IOException {
		serialPort.removeEventListener();
        serialPort.close();
        input.close();
        output.close();
	}
}
