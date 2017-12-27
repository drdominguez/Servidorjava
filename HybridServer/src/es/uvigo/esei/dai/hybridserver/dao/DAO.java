package es.uvigo.esei.dai.hybridserver.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO {

	public List<String> listPages() throws SQLException;
	public boolean addPage (String uuid,String content) throws SQLException;
	public boolean deletePage (String uuid) throws SQLException;
	public String getPage (String uuid) throws SQLException;
	public boolean exists (String uuid) throws SQLException;

}
