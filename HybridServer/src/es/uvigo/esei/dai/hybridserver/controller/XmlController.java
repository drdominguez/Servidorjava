package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;
import es.uvigo.esei.dai.hybridserver.dao.XMLDAODB;

public class XmlController {

	private XMLDAODB xmldao;
	private Map<ServerConfiguration, WebServices> remoteServices;
	public XmlController(Connection connect,remoteServers serv) {
		this.xmldao = new XMLDAODB(connect);
		this.remoteServices=serv.getRemotes();
	}

	public List<String> listPages() throws SQLException {
		
		List<String> result=this.xmldao.listPages();
		Iterator<String> it;
		for (Map.Entry<ServerConfiguration, WebServices> entry : this.remoteServices.entrySet()) {
			it=entry.getValue().uuidXML().iterator();
			while(it.hasNext())
			result.add(it.next())  ;			
		}
		
		return result;
	
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.xmldao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.xmldao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		String content=this.xmldao.getPage(uuid);
//		this.remoteServices.forEach((k,v) -> 
//		content=v.contentHTML(uuid),
//		content!=null
//				);
		if(content==null) {
		for (Map.Entry<ServerConfiguration, WebServices> entry : this.remoteServices.entrySet()) {
			content = entry.getValue().contentXML(uuid);
			if (content != null)
				break;
			
		}
		}
        
		return content;
	}
	public boolean exist(String uuid) throws SQLException {
		return this.xmldao.exists(uuid);
	}
}
