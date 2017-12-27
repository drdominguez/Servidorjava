package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;



import es.uvigo.esei.dai.hybridserver.dao.HTMLDAODB;

public class HtmlController {


	HTMLDAODB htmldao;

	public HtmlController(Connection conect) {
	this.htmldao=new HTMLDAODB(conect);
	}

	public List<String> listPages() throws SQLException {
		return this.htmldao.listPages();
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.htmldao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.htmldao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		return this.htmldao.getPage(uuid);
	}
}
