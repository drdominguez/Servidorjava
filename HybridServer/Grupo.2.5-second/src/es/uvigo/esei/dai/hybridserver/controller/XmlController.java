package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import es.uvigo.esei.dai.hybridserver.Configuration;
import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.dao.XMLDAODB;

public class XmlController {

	private RemoteServers serv;
	private XMLDAODB xmldao;

	public XmlController(Configuration conf, RemoteServers serv) {
		this.serv = serv;
		this.xmldao = new XMLDAODB(conf);

	}

	public List<String> listPages() throws SQLException {
		List<String> result = this.xmldao.listPages();
		Iterator<String> it;
		for (WebServices webService : this.serv.getRemotes().values()) {
			it = webService.uuidXML().iterator();
			while (it.hasNext())
				result.add(it.next());
		}

		return result;
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.xmldao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		boolean content = this.xmldao.deletePage(uuid);
		if (content) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.deleteXML(uuid);
				if (content)
					break;
			}
		}
		return content;
	}

	public String getPage(String uuid) throws SQLException {
		String content = this.xmldao.getPage(uuid);
		if (content == null) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.contentXML(uuid);
				if (content != null)
					break;
			}
		}
		return content;
	}
}
