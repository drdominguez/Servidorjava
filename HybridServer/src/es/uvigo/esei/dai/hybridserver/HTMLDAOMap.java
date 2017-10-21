package es.uvigo.esei.dai.hybridserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HTMLDAOMap implements HTMLDAO {
	Map<String, String> pages;

	public HTMLDAOMap(Map<String, String> pages) {
		this.pages = pages;
	}

	@Override
	public List<String> listPages() {
		Iterator it = pages.entrySet().iterator();
		List<String> lista = new ArrayList<String>();
		pages.forEach((k,v) -> lista.add(v));
		return lista;
	}

	@Override
	public boolean addPage(String uuid, String content) {
		if (pages.containsKey(uuid)) {
			return false;
		} else {
			pages.put(uuid, content);
			return true;
		}
	}

	@Override
	public boolean deletePage(String uuid) {
		if (pages.containsKey(uuid)) {
			pages.remove(uuid);
			return true;
		} else
			return false;
	}

	@Override
	public String getPage(String uuid) {
		if (pages.containsKey(uuid)) {
			return pages.get(uuid);
		} else {
			return null;
		}
	}

}
