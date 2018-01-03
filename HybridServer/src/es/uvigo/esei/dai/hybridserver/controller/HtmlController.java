package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;
import es.uvigo.esei.dai.hybridserver.dao.HTMLDAODB;

public class HtmlController {


	HTMLDAODB htmldao;
	private RemoteServers serv;
	public HtmlController(Connection conect,RemoteServers serv) {
	this.htmldao=new HTMLDAODB(conect);
	this.serv=serv;
	}

	public List<String> listPages() throws SQLException {
		List<String> result=this.htmldao.listPages();
		Iterator<String> it;
		for (Map.Entry<ServerConfiguration, WebServices> entry : this.serv.getRemotes().entrySet()) {
			it=entry.getValue().uuidHTML().iterator();
			while(it.hasNext())
			result.add(it.next())  ;			
		}
		
		return result;
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.htmldao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.htmldao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		String content=this.htmldao.getPage(uuid);
		if(content==null) {
		for (Map.Entry<ServerConfiguration, WebServices> entry : this.serv.getRemotes().entrySet()) {
			content = entry.getValue().contentHTML(uuid);
			if (content != null)
				break;
			
		}
		}
        
		return content;
	}
}
