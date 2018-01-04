package es.uvigo.esei.dai.hybridserver;

import java.io.File;

import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class Launcher {
	public static void main(String[] args) throws Exception {
		HybridServer server;
		HTTPResponse response = new HTTPResponse();
		if (args.length == 0) {
			server = new HybridServer();
			server.start();
		} else if (args.length == 1) {
			Configuration conf = new XMLConfigurationLoader().load((new File(args[0])));
			if (conf == null) {
				System.err.println("Ha habido un error en el xml");
				response.setStatus(HTTPResponseStatus.S404);
			}
			server = new HybridServer(conf);
			server.start();
		}
	}
}
