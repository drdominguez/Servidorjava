package es.uvigo.esei.dai.hybridserver.controller;

import es.uvigo.esei.dai.hybridserver.Configuration;

public class FactoryControllerDB implements FactoryController {
	private RemoteServers serv;
	private Configuration conf;
	public FactoryControllerDB(Configuration conf) {
		serv = new RemoteServers(conf);
		this.conf=conf;
		
	}

	@Override
	public HtmlController createHTMLController() {
		return new HtmlController(this.conf, this.serv);
	}

	@Override
	public XmlController createXmlController() {
		return new XmlController(this.conf, this.serv);
	}

	@Override
	public XsdController createXsdController() {
		return new XsdController(this.conf, this.serv);
	}

	@Override
	public XsltController createXsltController() {
		return new XsltController(this.conf, this.serv);
	}

}
