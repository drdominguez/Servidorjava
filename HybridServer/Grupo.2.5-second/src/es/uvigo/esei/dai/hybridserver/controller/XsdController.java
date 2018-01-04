package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import es.uvigo.esei.dai.hybridserver.Configuration;
import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.dao.XSDDAODB;

public class XsdController {

	private RemoteServers serv;
	private XSDDAODB xsddao;

	public XsdController(Configuration conf, RemoteServers serv) {
		this.serv = serv;
		this.xsddao = new XSDDAODB(conf);
	}

	public List<String> listPages() throws SQLException {
		List<String> result = this.xsddao.listPages();
		Iterator<String> it;

		for (WebServices webService : this.serv.getRemotes().values()) {
			it = webService.uuidXSD().iterator();
			while (it.hasNext())
				result.add(it.next());
		}
		return result;
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.xsddao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		boolean content = this.xsddao.deletePage(uuid);
		if (content) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.deleteXSD(uuid);
				if (content)
					break;
			}
		}
		return content;
	}

	public String getPage(String uuid) throws SQLException {
		String content = this.xsddao.getPage(uuid);
		if (content == null) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.contentXSD(uuid);
				if (content != null)
					break;
			}
		}
		return content;
	}

	public boolean exist(String uuid) throws SQLException {
		boolean content = this.xsddao.exists(uuid);
		if (content) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.existXSD(uuid);
				if (content)
					break;
			}
		}
		return content;
	}
}
