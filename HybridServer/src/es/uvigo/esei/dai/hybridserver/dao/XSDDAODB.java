package es.uvigo.esei.dai.hybridserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.dao.DAO;

public class XSDDAODB implements DAO {
	
	private Connection connection;
	
	public XSDDAODB(Connection connection) {
		this.connection = connection;
	}
@Override
	public boolean addPage(String uuid, String content) {
		String query = "INSERT INTO XSD (uuid,content) VALUES(?,?)";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, uuid);
			statement.setString(2, content);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
@Override
	public boolean deletePage(String uuid) {
		String query = "DELETE FROM XSD WHERE uuid=?";
		
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1,uuid);
			statement.executeUpdate();
			deleteXslt(uuid);//Elimino los xslt que tengan el xsd asociado
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

public boolean deleteXslt(String xsd) {
	String query = "DELETE FROM XSLT WHERE xsd=?";//Elimino los Xslt con el xsd deseado
	
	try (PreparedStatement statement = this.connection.prepareStatement(query)) {
		statement.setString(1,xsd);
		statement.executeUpdate();
		return true;
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}

	@Override
	public String getPage(String uuid ) {
		String content = null;
		String query = "SELECT content FROM XSD WHERE uuid=?";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, uuid);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next()) {
					content = results.getString("content");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return content;
	}

	@Override
	public List<String>  listPages() {
		//StringBuilder content = new StringBuilder();
		List<String> list= new ArrayList<String>();
		String query = "SELECT uuid FROM XSD";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			try (ResultSet results = statement.executeQuery()) {
				while (results.next()) {
					//content.append(this.link(results.getString("uuid")));
					list.add(results.getString("uuid"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}



	@Override
	public boolean exists(String uuid) {
		String query = "SELECT * FROM XSD WHERE uuid=?";
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
	public String getContent(String uuid) {
		String content = null;
		String query = "SELECT content FROM XSD WHERE uuid=?";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, uuid);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next()) {
					content = results.getString("content");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return content;
	}
}