package es.uvigo.esei.dai.hybridserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HybridServer {
	private  int SERVICE_PORT = 8888;
	private Thread serverThread;
	private boolean stop;
	private HTMLDAO pages;
	private int numClient=50;

	public HybridServer(){
		numClient=50;
		SERVICE_PORT = 8888;
		pages=new HTMLDAODB("hsdb", "hsdbpass","jdbc:mysql://localhost:3306/hstestdb");
	}

	public HybridServer(Map<String, String> pages) {
		this.pages= new HTMLDAOMap(pages);
		SERVICE_PORT = 8888;
		numClient=50;
	}

	public HybridServer(Properties properties) throws SQLException {
		numClient=Integer.parseInt(properties.get("numClients").toString());
		SERVICE_PORT = Integer.parseInt(properties.get("port").toString());
		pages=new HTMLDAODB(properties);
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
