package es.uvigo.esei.dai.hybridserver;

import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.controller.FactoryControllerDB;
import es.uvigo.esei.dai.hybridserver.controller.HtmlController;
import es.uvigo.esei.dai.hybridserver.controller.XmlController;
import es.uvigo.esei.dai.hybridserver.controller.XsdController;
import es.uvigo.esei.dai.hybridserver.controller.XsltController;
import javax.jws.WebService;

@WebService(endpointInterface ="es.uvigo.esei.dai.hybridserver.WebServices", serviceName = "HybridServerService")
public class WebServicesImpl implements WebServices{
	private FactoryControllerDB create;
	public WebServicesImpl(FactoryControllerDB create) {
		this.create=create;
	}
	@Override
	public List<String> uuidHTML() throws SQLException {
		HtmlController htmlcontroller=create.createHTMLController();
		return htmlcontroller.listPages();
	}
	@Override
	public List<String> uuidXML() throws SQLException {
		XmlController xmlcontroller=create.createXmlController();
		return xmlcontroller.listPages();
	}
	@Override
	public List<String> uuidXSD() throws SQLException {
		XsdController xsdcontroller=create.createXsdController();
		return xsdcontroller.listPages();
	}
	@Override
	public List<String> uuidXSLT() throws SQLException {
		XsltController xsltcontroller=create.createXsltController();
		return xsltcontroller.listPages();
	}
	@Override
	public String contentHTML(String uuid) throws SQLException {
		HtmlController htmlcontroller=create.createHTMLController();
		return htmlcontroller.getPage(uuid);
	}
	@Override
	public String contentXML(String uuid) throws SQLException {
		XmlController xmlcontroller=create.createXmlController();
		return xmlcontroller.getPage(uuid);
	}
	@Override
	public String contentXSD(String uuid) throws SQLException {
		XsdController xsdcontroller=create.createXsdController();
		return xsdcontroller.getPage(uuid);
	}
	@Override
	public String contentXSLT(String uuid) throws SQLException {
		XsltController xsltcontroller=create.createXsltController();
		return xsltcontroller.getPage(uuid);
	}
	

}
