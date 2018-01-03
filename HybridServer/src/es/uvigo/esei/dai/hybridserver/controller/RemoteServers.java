package es.uvigo.esei.dai.hybridserver.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;

public class RemoteServers {
	private Configuration conf;

	public RemoteServers(Configuration conf) {
		this.conf = conf;
	}

	public Map<ServerConfiguration, WebServices> getRemotes() {
		Map<ServerConfiguration, WebServices> remoteServices = new LinkedHashMap<>();

		for (ServerConfiguration server : conf.getServers()) {
			URL url;
			try {
				url = new URL(server.getWsdl());
				QName name = new QName(server.getNamespace(), server.getService());
				// System.out.println("\n\n\n"+server.getNamespace()+"\n\n\n");
				Service service = Service.create(url, name);
				WebServices webservice = service.getPort(WebServices.class);
				remoteServices.put(server, webservice);
			} catch (MalformedURLException | WebServiceException e) {
				System.err.println("Un servidor caido no lloreis por el");
			}
		}
		
		return remoteServices;
	}

}
