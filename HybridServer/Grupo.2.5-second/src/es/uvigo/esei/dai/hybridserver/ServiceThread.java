package es.uvigo.esei.dai.hybridserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import es.uvigo.esei.dai.hybridserver.controller.FactoryControllerDB;
import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;

public class ServiceThread extends Thread {
	private Socket socket;
	private BufferedReader reader;
	private FactoryControllerDB pages;
	private PrintWriter writer;

	public ServiceThread(Socket socket, FactoryControllerDB pages2) throws IOException {// Lista de controladores 2ndo
																						// argumento
		this.socket = socket;
		this.pages = pages2;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(socket.getOutputStream());
	}

	public void run() {
		try (Socket socket = this.socket) {
			HTTPRequest request = new HTTPRequest(this.reader);
			Manager manager = new Manager(request, this.pages);
			manager.getResponse().print(writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HTTPParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
