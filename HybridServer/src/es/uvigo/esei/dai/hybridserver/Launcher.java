package es.uvigo.esei.dai.hybridserver;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Launcher {
	public static void main(String[] args) throws SQLException {
		HybridServer server= null;
		if(args.length == 1) {
			try {
				FileReader filereader = new FileReader(args[0]);
				Properties propiedades = new Properties();
				propiedades.load(filereader);
				//System.out.println(propiedades);
				server = new HybridServer(propiedades);
				filereader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (args.length == 0) {
			server = new HybridServer();
		} else {
			//Mensaje de error
			System.err.println("Demasiados parámetros");
		}
		server.start();
	}
}
