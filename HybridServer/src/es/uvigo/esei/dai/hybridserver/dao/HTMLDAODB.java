package es.uvigo.esei.dai.hybridserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class HTMLDAODB implements DAO {
	private String tabla = "HTML";
	Connection connection;

	
	public HTMLDAODB(Connection conect) {
		this.connection=conect;
	}
	
	

	@Override
	public List<String> listPages() throws SQLException {
		List<String> pagina = new LinkedList<>();
			try (Statement statement = this.connection.createStatement()) {
				try (ResultSet result = statement.executeQuery("SELECT * FROM HTML")) {
					while (result.next()) {
						pagina.add(result.getString("uuid"));
					}
				}
			}
		
		return pagina;
	}

	@Override
	public boolean addPage(String uuid, String content) throws SQLException {
		boolean resultado = false;
			try (PreparedStatement statement = this.connection
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
		
		return resultado;
	}

	@Override
	public boolean deletePage(String uuid) throws SQLException {
		boolean resultado = false;
			//Creacion de la consulta
			try (PreparedStatement statement = this.connection
				.prepareStatement("DELETE FROM " + tabla + " WHERE uuid = ?")) {
				statement.setString(1, uuid);
				//Compruebo si no se eliminó
				statement.executeUpdate();
				resultado = true;
			} catch (SQLException ex) {
				resultado = false;
				
			}
		
		return resultado;
	}

	@Override
	public String getPage(String uuid) throws SQLException {
		String resultado = null;
			try (PreparedStatement prepStatement = this.connection
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
	@Override
	public boolean exists(String uuid) throws SQLException {
		
		String query = "SELECT * FROM XML WHERE uuid=?";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, uuid);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next())
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}

}
