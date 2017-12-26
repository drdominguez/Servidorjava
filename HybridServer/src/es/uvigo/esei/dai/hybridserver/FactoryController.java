package es.uvigo.esei.dai.hybridserver;

import es.uvigo.esei.dai.hybridserver.controller.HtmlController;
import es.uvigo.esei.dai.hybridserver.controller.XmlController;
import es.uvigo.esei.dai.hybridserver.controller.XsdController;
import es.uvigo.esei.dai.hybridserver.controller.XsltController;

public interface FactoryController {
	HtmlController createHTMLController();
	XmlController createXmlController();
	XsdController createXsdController();
	XsltController createXsltController();

}
