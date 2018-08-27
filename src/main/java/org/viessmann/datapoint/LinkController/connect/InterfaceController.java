package org.viessmann.datapoint.LinkController.connect;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class InterfaceController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(InterfaceController.class);
	
	private Map<String, SerialInterface> interfaces;
	
	public InterfaceController() {
		interfaces  = new HashMap<>();
	}
	
	public void createInterface(String commPort) {	
		if(!interfaces.containsKey(commPort)) {
			SerialInterface serialInterface = InterfaceFactory.createSerialInterface(commPort);
			interfaces.put(commPort, serialInterface);
		} else {
			String msg = String.format("{} commPort already created.", commPort);
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	}
	
	public void closeInterface(String commPort) {
		if(interfaces.containsKey(commPort)) {
			SerialInterface serialInterface = interfaces.get(commPort);
			serialInterface.close();
			interfaces.remove(commPort);
		} else {
			String msg = String.format("{} commPort not available.", commPort);
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	}
	
	public InterfaceStatus interfaceStatus(String commPort) {
		if(interfaces.containsKey(commPort)) {
			SerialInterface serialInterface = interfaces.get(commPort);
			if(serialInterface.isClosed()) {
				return InterfaceStatus.CLOSED;
			} else {
				return InterfaceStatus.OPEN;
			}
		} else {
			return InterfaceStatus.NOT_AVAILABLE;
		}
	}
}
