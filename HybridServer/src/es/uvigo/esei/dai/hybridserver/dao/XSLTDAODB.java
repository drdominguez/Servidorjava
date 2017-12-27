package es.uvigo.esei.dai.hybridserver.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XSLTDAODB {
	
	private Connection connection;
	
	public XSLTDAODB(Connection connection) {
		this.connection = connection;
	}
public boolean addPage(String uuid, String content,String xsd) {
		String query = "INSERT INTO XSLT (uuid,content,xsd) VALUES(?,?,?)";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, uuid);
			statement.setString(2, content);
			statement.setString(3, xsd);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
public boolean deletePage(String uuid) {
		String query = "DELETE FROM XSLT WHERE uuid=?";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1,uuid);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPage(String uuid ) {
		String content = null;
		String query = "SELECT content FROM XSLT WHERE uuid=?";
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

	public List<String>  listPages() {
		//StringBuilder content = new StringBuilder();
		List<String> list= new ArrayList<String>();
		String query = "SELECT uuid FROM XSLT";
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



	public boolean exists(String uuid) {
		String query = "SELECT * FROM XSLT WHERE uuid=?";
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
		String query = "SELECT content FROM XSLT WHERE uuid=?";
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
	public String getXSD(String uuid) {
		String xsd = null;
		String content = null;
		String query = "SELECT XSD FROM XSLT WHERE uuid=?";
		try (PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(3, xsd);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next()) {
					content = results.getString("xsd");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		 System.out.println("Este es el xsd"+content);
		return content;
	}
}