package es.uvigo.esei.dai.hybridserver.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import es.uvigo.esei.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;

public class FactoryControllerDB implements FactoryController {
private Connection connect;
private Map<ServerConfiguration,Service> remoteServices;
private Iterator<ServerConfiguration> it;
private Configuration conf;
	public FactoryControllerDB(Configuration conf) throws MalformedURLException {
		this.conf=conf;
		try {
			this.connect = DriverManager.getConnection(conf.getDbURL(),conf.getDbUser(),conf.getDbPassword());
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HtmlController createHTMLController() {
		
		
		return new HtmlController(this.connect,this.it);
	}

	@Override
	public XmlController createXmlController() {
		return new XmlController(this.connect);
	}

	@Override
	public XsdController createXsdController() {
		return new XsdController(this.connect);
	}

	@Override
	public XsltController createXsltController() {
		return new XsltController(this.connect);
	}
	public Map<ServerConfiguration,Service> getRemotes() throws MalformedURLException {
		this.it=this.conf.getServers().iterator();
		while(it.hasNext()) {
			ServerConfiguration server=it.next();
			URL url = new URL(server.getWsdl());
            QName name = new QName(server.getNamespace(), server.getService());
            Service service = Service.create(url, name);
            this.remoteServices.put(server, service);

		}
		return this.remoteServices;
	}

}
