package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.XSLTDAODB;

public class XsltController {

	private XSLTDAODB xsltdao;

	public XsltController(Connection connect) {
		this.xsltdao = new XSLTDAODB(connect);
	}

	public List<String> listPages() throws SQLException {
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

	public String getXSD(String uuid) {
		return this.xsltdao.getXSD(uuid);
	}
	public boolean exist(String uuid) throws SQLException {
		return this.xsltdao.exists(uuid);
	}
}
