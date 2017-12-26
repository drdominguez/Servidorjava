package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.XSLTDAODB;

public class XsltController {

	private XSLTDAODB xsltdao;

	public XsltController(XSLTDAODB xsltdao) {
		this.xsltdao = xsltdao;
	}

	public List<String> listPages() throws SQLException {
		System.out.println("Entró en el controlador");
		return this.xsltdao.listPages();
	}

	public boolean addPage(String uuid, String content,String xsd) throws SQLException {
		return this.xsltdao.addPage(uuid, content,xsd);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.xsltdao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		return this.xsltdao.getPage(uuid);
	}
}
