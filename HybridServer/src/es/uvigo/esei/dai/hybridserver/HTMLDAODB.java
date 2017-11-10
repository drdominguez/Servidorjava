package es.uvigo.esei.dai.hybridserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.xml.internal.txw2.Document;

public class HTMLDAODB implements HTMLDAO {
	private Connection Conexion;
	private String user;
	private String pass;
	private String url;
	private String tabla = "HTML";

	public HTMLDAODB(Properties properties) {
		this.user = properties.getProperty("db.user");
		this.pass = properties.getProperty("db.password");
		this.url = properties.getProperty("db.url");
	}
	
	public HTMLDAODB(String user, String pass, String url) {
		this.user = user;
		this.pass = pass;
		this.url = url;
	}

	public void MySQLConnection(String user, String pass, String db_name) {
		try {
			Conexion = DriverManager.getConnection(db_name, user, pass);
		} catch (SQLException ex) {
			Logger.getLogger(HTMLDAODB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void closeConnection() {
		try {
			Conexion.close();
		} catch (SQLException ex) {
			Logger.getLogger(HTMLDAODB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public List<Pagina> listPages() {
		this.MySQLConnection(this.user, this.pass, this.url);
		List<Pagina> pagina = new LinkedList<Pagina>();
		try {
			String Query = "SELECT * FROM " + tabla;
			Statement st = Conexion.createStatement();
			java.sql.ResultSet resultSet;
			resultSet = st.executeQuery(Query);
			while (resultSet.next()) {
				pagina.add(new Pagina(resultSet.getString("uuid"),resultSet.getString("content")));
			}
		} catch (SQLException ex) {
		}
		this.closeConnection();
		return pagina;
	}

	@Override
	public boolean addPage(String uuid, String content) throws SQLException {
		boolean resultado = false;
		this.MySQLConnection(this.user, this.pass, this.url);
		try {
			String Query = "INSERT INTO HTML(uuid,content) VALUES(" + "\"" + uuid + "\"" + ","  + "\""+ content +"\"" + ")";
			Statement st = Conexion.createStatement();
			st.executeUpdate(Query);
			resultado = true;
		} catch (SQLException ex) {
			resultado = false;
		}
		this.closeConnection();

		return resultado;
	}

	@Override
	public boolean deletePage(String uuid) {
		boolean resultado = false;
		this.MySQLConnection(this.user, this.pass, this.url);
		try {
			String Query = "DELETE FROM " + tabla + " WHERE uuid = \"" + uuid + "\"";
			Statement st = Conexion.createStatement();
			st.executeUpdate(Query);
			resultado = true;

		} catch (SQLException ex) {

			resultado = false;
		}
		this.closeConnection();
		return resultado;
	}

	@Override
	public String getPage(String uuid) {
		this.MySQLConnection(this.user, this.pass, this.url);
		String resultado;
		try {
			String Query = "SELECT content FROM " + tabla + " WHERE uuid=\""+uuid+"\"";
			Statement st = Conexion.createStatement();
			java.sql.ResultSet resultSet;
			resultSet = st.executeQuery(Query);
			resultSet.next();
			resultado = resultSet.getString("content");

		} catch (SQLException ex) {
			resultado = null;
		}
		this.closeConnection();
		return resultado;
	}

}
