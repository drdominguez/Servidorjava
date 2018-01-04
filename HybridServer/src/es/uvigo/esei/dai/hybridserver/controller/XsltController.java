package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import es.uvigo.esei.dai.hybridserver.Configuration;
import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.dao.XSLTDAODB;

public class XsltController {

	private RemoteServers serv;
	private XSLTDAODB xsltdao;

	public XsltController(Configuration conf, RemoteServers serv) {
		this.serv = serv;
		this.xsltdao = new XSLTDAODB(conf);
	}

	public List<String> listPages() throws SQLException {
		List<String> result = this.xsltdao.listPages();
		Iterator<String> it;
		for (WebServices webService : this.serv.getRemotes().values()) {
			it = webService.uuidXSLT().iterator();
			while (it.hasNext())
				result.add(it.next());
		}
		return result;
	}

	public boolean addPage(String uuid, String content, String xsd) throws SQLException {
		return this.xsltdao.addPage(uuid, content, xsd);
	}

	public boolean deletePage(String uuid) throws SQLException {
		boolean content = this.xsltdao.deletePage(uuid);
		if (content) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.deleteXSLT(uuid);
				if (content)
					break;
			}
		}
		return content;
	}

	public String getPage(String uuid) throws SQLException {
		String content = this.xsltdao.getPage(uuid);
		if (content == null) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.contentXSLT(uuid);
				if (content != null)
					break;
			}
		}
		return content;
	}

	public String getXSD(String uuid) throws SQLException {
		String content = this.xsltdao.getXSD(uuid);
		if (content == null) {
			for (WebServices webService : this.serv.getRemotes().values()) {
				content = webService.getXSD(uuid);
				if (content != null)
					break;
			}
		}
		return content;
	}
}
