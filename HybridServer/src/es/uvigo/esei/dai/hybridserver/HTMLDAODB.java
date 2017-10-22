package es.uvigo.esei.dai.hybridserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HTMLDAODB implements HTMLDAO {
	private  DBService db=null;
	
	public HTMLDAODB(DBService database) {
		this.db = database;
	}
	@Override
	public List<String> listPages() {

		return null;
	}

	@Override
	public boolean addPage(String uuid, String content) {
		return false;
	}

	@Override
	public boolean deletePage(String uuid) {
		return false;
	}

	@Override
	public String getPage(String uuid) {

	        return null;
	    }

		

}
