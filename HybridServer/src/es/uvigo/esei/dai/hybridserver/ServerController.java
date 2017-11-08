package es.uvigo.esei.dai.hybridserver;

import java.sql.SQLException;
import java.util.List;

public class ServerController {
	private HTMLDAO dao;
	
	public ServerController(HTMLDAO dao) {
		this.dao = dao;
	}
	public List <String> ListPages(){
		return dao.listPages();
	}
	public boolean deletePage(String uuid) {
		return dao.deletePage(uuid);
	}
	public boolean addPage(String uuid, String content) throws SQLException {
		return dao.addPage(uuid,content);
	}
	public String getPage(String uuid) {
		return dao.getPage(uuid);
	}
}
