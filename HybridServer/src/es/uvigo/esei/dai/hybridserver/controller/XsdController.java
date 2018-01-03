package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;
import es.uvigo.esei.dai.hybridserver.dao.XSDDAODB;

public class XsdController {

	private XSDDAODB xsddao;
	private RemoteServers serv;
	public XsdController(Connection connect,RemoteServers serv) {
		this.xsddao = new XSDDAODB(connect);
		this.serv=serv;
	}

	public List<String> listPages() throws SQLException {
		List<String> result=this.xsddao.listPages();
		Iterator<String> it;
		for (Map.Entry<ServerConfiguration, WebServices> entry : this.serv.getRemotes().entrySet()) {
			it=entry.getValue().uuidXSD().iterator();
			while(it.hasNext())
			result.add(it.next())  ;			
		}
		
		return result;
	}

	public boolean addPage(String uuid, String content) throws SQLException {
		return this.xsddao.addPage(uuid, content);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.xsddao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		String content=this.xsddao.getPage(uuid);
//		this.remoteServices.forEach((k,v) -> 
//		content=v.contentHTML(uuid),
//		content!=null
//				);
		if(content==null) {
		for (Map.Entry<ServerConfiguration, WebServices> entry :  this.serv.getRemotes().entrySet()) {
			content = entry.getValue().contentXSD(uuid);
			if (content != null)
				break;
			
		}
		}
        
		return content;
	
	
	}
	public boolean exist(String uuid) throws SQLException {
		return this.xsddao.exists(uuid);
	}
}
