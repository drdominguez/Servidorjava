package es.uvigo.esei.dai.hybridserver.controller;

import es.uvigo.es.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.dao.HTMLDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XMLDAODB;

public class FactoryControllerDB implements FactoryController {
private Configuration conf;
	public FactoryControllerDB(Configuration conf) {
		this.conf=conf;
	}

	@Override
	public HtmlController createHTMLController() {
		return new HtmlController(new HTMLDAODB(this.conf));
	}

	@Override
	public XmlController createXmlController() {
		return new XmlController(new XMLDAODB(this.conf));
	}

	@Override
	public XsdController createXsdController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XsltController createXsltController() {
		// TODO Auto-generated method stub
		return null;
	}

}
