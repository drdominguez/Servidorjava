package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import es.uvigo.esei.dai.hybridserver.configuration.Configuration;

public class FactoryControllerDB implements FactoryController {
private Connection connect;
private remoteServers serv;

	public FactoryControllerDB(Configuration conf)  {
		serv=new remoteServers(conf);
		try {
			this.connect = DriverManager.getConnection(conf.getDbURL(),conf.getDbUser(),conf.getDbPassword());
			
			
			
		} catch (SQLException e) {
			
		}
		
	}

	@Override
	public HtmlController createHTMLController() {
		return new HtmlController(this.connect,this.serv);
	}

	@Override
	public XmlController createXmlController() {
		return new XmlController(this.connect,this.serv);
	}

	@Override
	public XsdController createXsdController() {
		return new XsdController(this.connect,this.serv);
	}

	@Override
	public XsltController createXsltController() {
		return new XsltController(this.connect,this.serv);
	}


}
