package es.uvigo.esei.dai.hybridserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBService {
    private static final Logger log = Logger.getLogger(DBService.class.getName());

	private final Properties config;
	
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
	    	this.connection = DriverManager.getConnection(
	    			config.getProperty("db.url"), 
	    			config.getProperty("db.user"), 
	    			config.getProperty("db.password") 
	    		);

	    	
    	}
    	   		
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