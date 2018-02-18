package logcollector;

import java.io.File;

import org.apache.log4j.*;
import org.apache.log4j.net.*;

public class LogServer {

	private final Logger logger = Logger.getLogger(LogServer.class);
	
	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("user.dir"));
		
		String[] arguments = {"4560", System.getProperty("user.dir") + File.separator + "Config/log4j-server.properties"};  

        SimpleSocketServer.main(arguments);  

	}
}
