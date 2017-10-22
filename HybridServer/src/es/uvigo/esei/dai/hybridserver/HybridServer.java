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
	protected static HTMLDAO pages;
	private static Properties propiedades=null;
	protected static HTMLDAODB BD;

	public HybridServer(){
		
	}

	public HybridServer(Map<String, String> pages) {
		HybridServer.pages= new HTMLDAOMap(pages);
	}

	public HybridServer(Properties properties) throws SQLException {
		this.propiedades=properties;
		
	}

	public int getPort() {
		return SERVICE_PORT;
	}
	public void start() {
		ExecutorService threadPool = Executors.newFixedThreadPool(50);
		
		this.serverThread = new Thread() {
			@Override
			public void run() {
				if(HybridServer.propiedades!=null) {
					BD=new HTMLDAODB(propiedades);
					SERVICE_PORT=Integer.parseInt(propiedades.get("port").toString());
				}
				try (final ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {
					while (true) {
						Socket socket = serverSocket.accept();
						if (stop)
							break;
							
						threadPool.execute(new ServiceThread(socket));
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
