package es.uvigo.esei.dai.hybridserver;

import java.sql.SQLException;
import java.util.List;

public interface HTMLDAO {

	public List<Pagina> listPages();
	public boolean addPage (String uuid,String content) throws SQLException;
	public boolean deletePage (String uuid);
	public String getPage (String uuid);

}
