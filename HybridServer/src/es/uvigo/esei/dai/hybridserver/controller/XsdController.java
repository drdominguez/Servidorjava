package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.XSDDAODB;

public class XsdController {

	private XSDDAODB xsddao;

	public XsdController(Connection connect) {
		this.xsddao = new XSDDAODB(connect);
	}

	public List<String> listPages() throws SQLException {
		return this.xsddao.listPages();
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.xsddao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.xsddao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		return this.xsddao.getPage(uuid);
	}
	public boolean exist(String uuid) throws SQLException {
		return this.xsddao.exists(uuid);
	}
}
