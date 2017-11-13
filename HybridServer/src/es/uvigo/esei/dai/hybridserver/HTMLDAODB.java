package es.uvigo.esei.dai.hybridserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


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

//	public void MySQLConnection(String user, String pass, String db_name) {
//		try {
//			Conexion = DriverManager.getConnection(db_name, user, pass);
//		} catch (SQLException ex) {
//			Logger.getLogger(HTMLDAODB.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}

//	public void closeConnection() {
//		try {
//			Conexion.close();
//		} catch (SQLException ex) {
//			Logger.getLogger(HTMLDAODB.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}

	@Override
	public List<String> listPages() throws SQLException {
		List<String> pagina = new LinkedList<>();

		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet result = statement.executeQuery("SELECT * FROM HTML")) {
					while (result.next()) {
						pagina.add(result.getString("uuid"));
					}
				}
			}
		}
		return pagina;
		
	
//		this.MySQLConnection(this.user, this.pass, this.url);
//		List<String> pagina = new LinkedList<String>();
//		try {
//			String Query = "SELECT * FROM " + tabla;
//			Statement st = Conexion.createStatement();
//			java.sql.ResultSet resultSet;
//			resultSet = st.executeQuery(Query);
//			while (resultSet.next()) {
//				pagina.add(resultSet.getString("uuid"));
//			}
//		} catch (SQLException ex) {
//		}
//		this.closeConnection();
//		return pagina;
	}

	@Override
	public boolean addPage(String uuid, String content) throws SQLException {
		boolean resultado = false;
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)) {
			try (PreparedStatement statement = connection
				.prepareStatement("INSERT INTO HTML (uuid, content) VALUES (?, ?)")) {
				statement.setString(1, uuid);
				statement.setString(2, content);
				//Compruebo si no se introduce
				int value = statement.executeUpdate();
				if (value != 1) {
					resultado=false;
					throw new SQLException("Error al añadir.");
				}
				resultado=true;
			} catch (SQLException e) {
				throw new SQLException(e);
			}
		}
		return resultado;
		
//		boolean resultado = false;
//		this.MySQLConnection(this.user, this.pass, this.url);
//		try {
//			String Query = "INSERT INTO HTML(uuid,content) VALUES(" + "\"" + uuid + "\"" + ","  + "\""+ content +"\"" + ")";
//			Statement st = Conexion.createStatement();
//			st.executeUpdate(Query);
//			resultado = true;
//		} catch (SQLException ex) {
//			resultado = false;
//		}
//		this.closeConnection();
//
//		return resultado;
	}

	@Override
	public boolean deletePage(String uuid) throws SQLException {
		boolean resultado = false;
		//Conexión a la base de datos
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)) {
			//Creacion de la consulta
			try (PreparedStatement statement = connection
				.prepareStatement("DELETE FROM " + tabla + " WHERE uuid = ?")) {
				statement.setString(1, uuid);
				//Compruebo si no se eliminó
				statement.executeUpdate();
				resultado = true;
			} catch (SQLException ex) {
				resultado = false;
				
			}
		}
		return resultado;
	}
	
		
//		boolean resultado = false;
//		this.MySQLConnection(this.user, this.pass, this.url);
//		try {
//			String Query = "DELETE FROM " + tabla + " WHERE uuid = \"" + uuid + "\"";
//			Statement st = Conexion.createStatement();
//			st.executeUpdate(Query);
//			resultado = true;
//
//		} catch (SQLException ex) {
//
//			resultado = false;
//		}
//		this.closeConnection();
//		return resultado;
//	}

	@Override
	public String getPage(String uuid) throws SQLException {
		String resultado = null;
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)) {
			try (PreparedStatement prepStatement = connection
				.prepareStatement("SELECT * FROM HTML " + "WHERE uuid = ? ")) {
				prepStatement.setString(1, uuid);

			try (ResultSet resultSet = prepStatement.executeQuery()) {
				if (resultSet.next()) {
					resultado = resultSet.getString("content");
				}
				return resultado;
				}
			}
		}
	}
	
//		this.MySQLConnection(this.user, this.pass, this.url);
//		String resultado;
//		try {
//			String Query = "SELECT content FROM " + tabla + " WHERE uuid=\""+uuid+"\"";
//			Statement st = Conexion.createStatement();
//			java.sql.ResultSet resultSet;
//			resultSet = st.executeQuery(Query);
//			resultSet.next();
//			resultado = resultSet.getString("content");
//
//		} catch (SQLException ex) {
//			resultado = null;
//		}
//		this.closeConnection();
//		return resultado;
//	}

}
