package es.uvigo.esei.dai.hybridserver;


import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.HTMLDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XMLDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XSDDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XSLTDAODB;

import javax.jws.WebService;

@WebService(endpointInterface = "es.uvigo.esei.dai.hybridserver.WebServices", serviceName = "HybridServerService")
public class WebServicesImpl implements WebServices {
	private HTMLDAODB htmldao;
	private XSDDAODB xsddao;
	private XSLTDAODB xsltdao;
	private XMLDAODB xmldao;

	public WebServicesImpl(Configuration conf) {
	
			this.xmldao = new XMLDAODB(conf);
			this.xsddao = new XSDDAODB(conf);
			this.xsltdao = new XSLTDAODB(conf);
			this.htmldao = new HTMLDAODB(conf);

		
	}

	@Override
	public List<String> uuidHTML() {
		return htmldao.listPages();
	}

	@Override
	public List<String> uuidXML() {
		return xmldao.listPages();
	}

	@Override
	public List<String> uuidXSD() {
		return xsddao.listPages();
	}

	@Override
	public List<String> uuidXSLT() {
		return xsltdao.listPages();
	}

	@Override
	public String contentHTML(String uuid) {
		return htmldao.getPage(uuid);
	}

	@Override
	public String contentXML(String uuid) {
		return xmldao.getPage(uuid);
	}

	@Override
	public String contentXSD(String uuid){
		return xsddao.getPage(uuid);
	}

	@Override
	public String getXSD(String uuid) {
		return xsltdao.getXSD(uuid);
	}

	@Override
	public String contentXSLT(String uuid) {
		return xsltdao.getPage(uuid);
	}

	@Override
	public boolean deleteHTML(String uuid) {
		return htmldao.deletePage(uuid);
	}

	@Override
	public boolean deleteXML(String uuid) {
		return xmldao.deletePage(uuid);
	}

	@Override
	public boolean deleteXSD(String uuid) {
		return xsddao.deletePage(uuid);
	}

	@Override
	public boolean deleteXSLT(String uuid) {
		return xsltdao.deletePage(uuid);
	}
	
	@Override
	public boolean existXSD(String uuid) {
		return xsddao.deletePage(uuid);
	}

}
