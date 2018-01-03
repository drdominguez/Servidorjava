package es.uvigo.esei.dai.hybridserver;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface WebServices {
	@WebMethod
	public List<String> uuidHTML() throws SQLException;
	@WebMethod
	public List<String> uuidXML() throws SQLException;
	@WebMethod
	public List<String> uuidXSD() throws SQLException;
	@WebMethod
	public List<String> uuidXSLT() throws SQLException;
	@WebMethod
	public String contentHTML(String uuid) throws SQLException;
	@WebMethod
	public String contentXML(String uuid) throws SQLException;
	@WebMethod
	public String contentXSD(String uuid) throws SQLException;
	@WebMethod
	public String contentXSLT(String uuid) throws SQLException;
	@WebMethod
	public String getXSD(String uuid) throws SQLException;
}
