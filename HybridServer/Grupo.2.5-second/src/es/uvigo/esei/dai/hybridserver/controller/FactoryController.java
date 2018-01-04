package es.uvigo.esei.dai.hybridserver.controller;

public interface FactoryController {
	HtmlController createHTMLController();

	XmlController createXmlController();

	XsdController createXsdController();

	XsltController createXsltController();
}
