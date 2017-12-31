package es.uvigo.esei.dai.hybridserver.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;

public class remoteServers {
	private Map<ServerConfiguration,WebServices> remoteServices;
	public remoteServers(Configuration conf) {
		this.remoteServices=new LinkedHashMap<>();
		Iterator <ServerConfiguration>it=conf.getServers().iterator();
		while(it.hasNext()) {
			ServerConfiguration server=it.next();
			URL url;
			try {
				url = new URL(server.getWsdl());
				QName name = new QName(server.getNamespace(), server.getService()); 
				//System.out.println("\n\n\n"+server.getNamespace()+"\n\n\n");
				Service service = Service.create(url, name);
				WebServices webservice =service.getPort(WebServices.class);
				this.remoteServices.put(server, webservice);
			} catch (MalformedURLException e) {
				System.err.println("Un servidor caido no lloreis por el");
			}

		}
	}
	public Map<ServerConfiguration,WebServices> getRemotes()  {
		
		return this.remoteServices;
	}

}
