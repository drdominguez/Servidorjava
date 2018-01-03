package es.uvigo.esei.dai.hybridserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.dao.HTMLDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XMLDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XSDDAODB;
import es.uvigo.esei.dai.hybridserver.dao.XSLTDAODB;

import javax.jws.WebService;

@WebService(endpointInterface ="es.uvigo.esei.dai.hybridserver.WebServices", serviceName = "HybridServerService")
public class WebServicesImpl implements WebServices{
	private HTMLDAODB htmldao;
	private XSDDAODB xsddao;
	private XSLTDAODB xsltdao;
	private XMLDAODB xmldao;
	public WebServicesImpl(Configuration conf) {
		try {
			Connection connect = DriverManager.getConnection(conf.getDbURL(),conf.getDbUser(),conf.getDbPassword());
			this.xmldao = new XMLDAODB(connect);
			this.xsddao = new XSDDAODB(connect);
			this.xsltdao = new XSLTDAODB(connect);
			this.htmldao=new HTMLDAODB(connect);
			
			
		} catch (SQLException e) {
			
		}
		
	}
	@Override
	public List<String> uuidHTML() throws SQLException {
		return htmldao.listPages();
	}
	@Override
	public List<String> uuidXML() throws SQLException {
		return xmldao.listPages();
	}
	@Override
	public List<String> uuidXSD() throws SQLException {
		return xsddao.listPages();
	}
	@Override
	public List<String> uuidXSLT() throws SQLException {
		return xsltdao.listPages();
	}
	@Override
	public String contentHTML(String uuid) throws SQLException {
		return htmldao.getPage(uuid);
	}
	@Override
	public String contentXML(String uuid) throws SQLException {
		return xmldao.getPage(uuid);
	}
	@Override
	public String contentXSD(String uuid) throws SQLException {
		return xsddao.getPage(uuid);
	}
	@Override
	public String getXSD(String uuid) throws SQLException {
		return xsltdao.getXSD(uuid);
	}
	@Override
	public String contentXSLT(String uuid) throws SQLException {
		return xsltdao.getPage(uuid);
	}
	

}
