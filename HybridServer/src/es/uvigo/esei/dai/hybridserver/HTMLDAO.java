package es.uvigo.esei.dai.hybridserver;

import java.util.List;

public interface HTMLDAO {

	public List<String> listPages();
	public boolean addPage (String uuid,String content);
	public boolean deletePage (String uuid);
	public String getPage (String uuid);

}
