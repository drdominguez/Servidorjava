package es.uvigo.esei.dai.hybridserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HTMLDAODB implements HTMLDAO {
	private Properties properties;
	private HTMLDAODB db;
	private static Connection Conexion;
	String user;
	String pass;
	String url;
	private static final String tabla="HTML";
	private List <String> list;
	public HTMLDAODB(Properties properties) {
		
		
		 this.user=properties.getProperty("db.user");
		this.pass=properties.getProperty("db.password");
		this.url=properties.getProperty("db.url");
		
	}
	 
    public void MySQLConnection(String user, String pass, String db_name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexion = DriverManager.getConnection(db_name, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HTMLDAODB.class.getName()).log(Level.SEVERE, null, ex);
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
	public List<String> listPages() {
		this.MySQLConnection(this.user, this.pass, this.url);
		List list = new ArrayList();
        try {
            String Query = "SELECT * FROM " + tabla;
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);
            while (resultSet.next()) {
                list.add(resultSet.getString("uuid")); 
                
            }
        } catch (SQLException ex) { }
        this.closeConnection();
        return list; 
	}

	@Override
	public boolean addPage(String uuid, String content) {
		boolean resultado;
		this.MySQLConnection(this.user, this.pass, this.url);
		try {
			
            String Query = "INSERT INTO HTML(uuid,content) VALUES("
                     + uuid+","+ content +  ")";

                  
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
           resultado=true;
        } catch (SQLException ex) {
           resultado=false;
        }
		this.closeConnection();
		return resultado;
	}

	@Override
	public boolean deletePage(String uuid) {
		boolean resultado=false;
		this.MySQLConnection(this.user, this.pass, this.url);
		try {
            String Query = "DELETE FROM " + tabla + " WHERE uuid = \"" + uuid + "\"";
            Statement st = Conexion.createStatement();
            st.executeUpdate(Query);
            resultado=true;
 
        } catch (SQLException ex) {
            
            resultado=false;
        }
		this.closeConnection();
		return resultado;
	}

	@Override
	public String getPage(String uuid) {
		this.MySQLConnection(this.user, this.pass, this.url);
		String resultado;
		try {
			String Query = "SELECT "+uuid+" FROM " + tabla;
			 Statement st = Conexion.createStatement();
	         java.sql.ResultSet resultSet;
	            resultSet = st.executeQuery(Query);
	            
            resultado=resultSet.toString();
 
        } catch (SQLException ex) {
            
           resultado=null;
        }
		this.closeConnection();
	        return resultado;
	    }

		

}
