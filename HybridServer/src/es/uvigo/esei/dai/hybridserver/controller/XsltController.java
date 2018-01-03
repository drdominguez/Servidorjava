package es.uvigo.esei.dai.hybridserver.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.uvigo.esei.dai.hybridserver.WebServices;
import es.uvigo.esei.dai.hybridserver.configuration.ServerConfiguration;
import es.uvigo.esei.dai.hybridserver.dao.XSLTDAODB;

public class XsltController {

	private XSLTDAODB xsltdao;
	private RemoteServers serv;
	public XsltController(Connection connect, RemoteServers serv) {
		this.xsltdao = new XSLTDAODB(connect);
		this.serv=serv;
	}

	public List<String> listPages() throws SQLException {
		List<String> result=this.xsltdao.listPages();
		Iterator<String> it;
		for (Map.Entry<ServerConfiguration, WebServices> entry :this.serv.getRemotes().entrySet()) {
			it=entry.getValue().uuidXSLT().iterator();
			while(it.hasNext())
			result.add(it.next())  ;			
		}
		
		return result;
	}

	public boolean addPage(String uuid, String content,String xsd) throws SQLException {
		return this.xsltdao.addPage(uuid, content,xsd);
	}

	public boolean deletePage(String uuid) throws SQLException {
		return this.xsltdao.deletePage(uuid);
	}

	public String getPage(String uuid) throws SQLException {
		String content=this.xsltdao.getPage(uuid);
//		this.remoteServices.forEach((k,v) -> 
//		content=v.contentHTML(uuid),
//		content!=null
//				);
		if(content==null) {
		for (Map.Entry<ServerConfiguration, WebServices> entry : this.serv.getRemotes().entrySet()) {
			content = entry.getValue().contentXSLT(uuid);
			if (content != null)
				break;
			
		}
		}
        
		return content;
	
	}

	public String getXSD(String uuid) {
		return this.xsltdao.getXSD(uuid);
	}
	public boolean exist(String uuid) throws SQLException {
		return this.xsltdao.exists(uuid);
	}
}
