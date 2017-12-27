package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import es.uvigo.es.dai.hybridserver.configuration.Configuration;

public class FactoryControllerDB implements FactoryController {
private Connection connect;
	public FactoryControllerDB(Configuration conf) {
		try {
			this.connect = DriverManager.getConnection(conf.getDbURL(),conf.getDbUser(),conf.getDbPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HtmlController createHTMLController() {
		return new HtmlController(this.connect);
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

}
