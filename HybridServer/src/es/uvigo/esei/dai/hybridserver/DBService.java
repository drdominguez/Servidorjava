

package es.uvigo.esei.dai.hybridserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBService {
    private Logger log = Logger.getLogger(DBService.class.getName());

	private Properties config;
	
	private Connection connection;

    public DBService(Properties conf) {
    	this.config = conf;
    	
    }
    
    /**
     * Inicia el servicio Database
     */    
    public void start() throws SQLException {
    	System.out.println("*****************"+config.getProperty("db.url")+"*****************"+config.getProperty("db.user")+"*****************"+config.getProperty("db.password"));
    	if (this.connection == null) {
    		System.out.println("If");
	    	this.connection = DriverManager.getConnection(
	    			config.getProperty("db.url"),
	    			config.getProperty("db.user"),
	    			config.getProperty("db.password")
	    		);
	    	System.out.println("Entro: "+config.getProperty("db.user"));
	    	
    	}
    		System.out.println("Sale");
    	   		
    		log.log(Level.INFO, "Cliente DB iniciado. <" +this.config.getProperty("db.url") + ">");
    	
    }

    /**
     * Inicia el servicio Database
     */
    public void stop() throws SQLException {
    	if (this.connection != null) {
	    	this.connection.close();
	    	this.connection = null;
    		log.log(Level.INFO, "Cliente DB parado.");
    	}
    }

	/**
	 * Obtiene la configuración del servidor.
	 * 
	 * @return Objeto de configuración.
	 */
    public Connection getConnection() {
    	return this.connection;
    }
}