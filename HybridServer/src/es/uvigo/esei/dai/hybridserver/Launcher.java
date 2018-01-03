package es.uvigo.esei.dai.hybridserver;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import es.uvigo.esei.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.configuration.XMLConfigurationLoader;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class Launcher {
	public static void main(String[] args) throws Exception{
		HybridServer server;
		HTTPResponse response = new HTTPResponse();
		if(args.length==0) {
			server = new HybridServer();
			server.start();
		}
		else if(args.length==1) {
			Configuration conf=new XMLConfigurationLoader().load((new File (args[0])));
			if(conf==null) {
				System.err.println("Ha habido un error en el xml");
			response.setStatus(HTTPResponseStatus.S404);
			}
			server = new HybridServer(conf);
			server.start();
		
		}
		else {
			
		}

	
	}
}
	
