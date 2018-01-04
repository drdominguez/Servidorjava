package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import es.uvigo.esei.dai.hybridserver.Configuration;
import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.dao.HTMLDAODB;

public class HtmlController {

	private RemoteServers serv;
	private HTMLDAODB htmldao;

	public HtmlController(Configuration conf, RemoteServers serv) {
		this.serv = serv;
		this.htmldao = new HTMLDAODB(conf);

	}

	public List<String> listPages() throws SQLException {
		List<String> result = this.htmldao.listPages();
		Iterator<String> it;
		for (WebServices webService : this.serv.getRemotes().values()) {
			it = webService.uuidHTML().iterator();
			while (it.hasNext())
				result.add(it.next());
		}
		return result;
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.htmldao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		boolean content = this.htmldao.deletePage(uuid);
		if (content) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.deleteHTML(uuid);
				if (content)
					break;
			}
		}
		return content;
	}

	public String getPage(String uuid) throws SQLException {
		String content = this.htmldao.getPage(uuid);
		if (content == null) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.contentHTML(uuid);
				if (content != null)
					break;
			}
		}
		return content;
	}
}
