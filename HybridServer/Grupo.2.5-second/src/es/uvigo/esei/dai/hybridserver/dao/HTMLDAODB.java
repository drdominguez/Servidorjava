package es.uvigo.esei.dai.hybridserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.Configuration;

public class HTMLDAODB implements DAO {
	private String tabla = "HTML";
	Configuration conf;

	public HTMLDAODB(Configuration conf) {
		this.conf = conf;
	}

	public Connection connect() {
		try {
			Connection connect = DriverManager.getConnection(conf.getDbURL(), conf.getDbUser(), conf.getDbPassword());

			return connect;
		} catch (SQLException e) {
			System.err.println("No se ha podido establecer conexion con la bd");
		}
		return null;
	}

	@Override
	public List<String> listPages() {
		Connection connect = connect();
		List<String> list = new ArrayList<String>();
		String query = "SELECT uuid FROM HTML";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			try (ResultSet results = statement.executeQuery()) {
				while (results.next()) {
					list.add(results.getString("uuid"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean addPage(String uuid, String content) throws SQLException {
		Connection connect = connect();
		boolean resultado = false;
		try (PreparedStatement statement = connect.prepareStatement("INSERT INTO HTML (uuid, content) VALUES (?, ?)")) {
			statement.setString(1, uuid);
			statement.setString(2, content);
			// Compruebo si no se introduce
			int value = statement.executeUpdate();
			if (value != 1) {
				resultado = false;
				throw new SQLException("Error al añadir.");
			}
			resultado = true;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		connect.close();
		return resultado;
	}

	@Override
	public boolean deletePage(String uuid) {
		Connection connect = connect();
		boolean resultado = false;
		// Creacion de la consulta
		try (PreparedStatement statement = connect.prepareStatement("DELETE FROM " + tabla + " WHERE uuid = ?")) {
			statement.setString(1, uuid);
			// Compruebo si no se eliminó
			statement.executeUpdate();
			resultado = true;
		} catch (SQLException ex) {
			resultado = false;

		}
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	}

	@Override
	public String getPage(String uuid) {
		Connection connect = connect();
		String content = null;
		String query = "SELECT content FROM HTML WHERE uuid=?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, uuid);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next()) {
					content = results.getString("content");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;
	}

	@Override
	public boolean exists(String uuid) throws SQLException {
		Connection connect = connect();
		String query = "SELECT * FROM XML WHERE uuid=?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, uuid);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next()) {
					try {
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return true;
				} else {
					try {
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return false;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
