package es.uvigo.esei.dai.hybridserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.uvigo.esei.dai.hybridserver.configuration.Configuration;
import es.uvigo.esei.dai.hybridserver.controller.FactoryControllerDB;


public class HybridServer {
	private  int SERVICE_PORT = 8888;
	private Thread serverThread;
	private boolean stop;
	private FactoryControllerDB pages;
	private int numClient=50;

	public HybridServer() {
		numClient=50;
		SERVICE_PORT = 8888;
		Configuration conf=new Configuration();
		
			pages=new FactoryControllerDB(conf);
		
	}
	public HybridServer(Properties properties) throws SQLException {
		numClient=Integer.parseInt(properties.get("numClients").toString());
		SERVICE_PORT = Integer.parseInt(properties.get("port").toString());
		Configuration conf =new Configuration();
		conf.setNumClients(Integer.parseInt(properties.getProperty("numClients")));
		conf.setHttpPort(Integer.parseInt(properties.getProperty("port")));
		conf.setDbUser(properties.getProperty("db.user"));
		conf.setDbPassword(properties.getProperty("db.password"));
		conf.setDbURL(properties.getProperty("db.url"));
			pages=new FactoryControllerDB(conf);
		
	}



	public HybridServer(Configuration load) {
		numClient=load.getNumClients();
		SERVICE_PORT = load.getHttpPort();
		
			pages=new FactoryControllerDB(load);
		
		
	}

	public int getPort() {
		return SERVICE_PORT;
	}
	public void start() {
		ExecutorService threadPool = Executors.newFixedThreadPool(numClient);
		
		this.serverThread = new Thread() {
			@Override
			public void run() {
				try (final ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {
					while (true) {
						Socket socket = serverSocket.accept();
						if (stop)
							break;
		 					
						threadPool.execute(new ServiceThread(socket, pages));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		this.stop = false;
		this.serverThread.start();
	}

	public void stop() {
		this.stop = true;

		try (Socket socket = new Socket("localhost", SERVICE_PORT)) {
			// Esta conexión se hace, simplemente, para "despertar" el hilo servidor
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			this.serverThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		this.serverThread = null;
	}
}
