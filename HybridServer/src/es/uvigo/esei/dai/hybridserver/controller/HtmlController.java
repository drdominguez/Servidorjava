package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.HTMLDAO;

public class HtmlController {

	private HTMLDAO htmldao;

	public HtmlController(HTMLDAO htmldao) {
		this.htmldao = htmldao;
	}

	public List<String> listPages() throws SQLException {
		System.out.println("Entró en el controlador");
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
