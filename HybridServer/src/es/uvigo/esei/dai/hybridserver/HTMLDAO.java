package es.uvigo.esei.dai.hybridserver;

import java.sql.SQLException;
import java.util.List;

public interface HTMLDAO {

	public List<String> listPages() throws SQLException;
	public boolean addPage (String uuid,String content) throws SQLException;
	public boolean deletePage (String uuid) throws SQLException;
	public String getPage (String uuid) throws SQLException;

}
