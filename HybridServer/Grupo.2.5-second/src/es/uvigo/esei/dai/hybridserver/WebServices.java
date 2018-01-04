package es.uvigo.esei.dai.hybridserver;


import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface WebServices {
	@WebMethod
	public List<String> uuidHTML();

	@WebMethod
	public List<String> uuidXML();

	@WebMethod
	public List<String> uuidXSD();

	@WebMethod
	public List<String> uuidXSLT();

	@WebMethod
	public String contentHTML(String uuid);

	@WebMethod
	public String contentXML(String uuid);

	@WebMethod
	public String contentXSD(String uuid);

	@WebMethod
	public String contentXSLT(String uuid);

	@WebMethod
	public String getXSD(String uuid);

	@WebMethod
	public boolean deleteHTML(String uuid);

	@WebMethod
	public boolean deleteXML(String uuid);

	@WebMethod
	public boolean deleteXSD(String uuid);

	@WebMethod
	public boolean deleteXSLT(String uuid);
	
	@WebMethod
	public boolean existXSD(String uuid);
}
