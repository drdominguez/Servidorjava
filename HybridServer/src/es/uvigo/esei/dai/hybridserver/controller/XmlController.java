package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.XMLDAODB;

public class XmlController {

	private XMLDAODB xmldao;

	public XmlController(Connection connect) {
		this.xmldao = new XMLDAODB(connect);
	}

	public List<String> listPages() throws SQLException {
		return this.xmldao.listPages();
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.xmldao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.xmldao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		return this.xmldao.getPage(uuid);
	}
	public boolean exist(String uuid) throws SQLException {
		return this.xmldao.exists(uuid);
	}
}
