package es.uvigo.esei.dai.hybridserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequestMethod;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;
import es.uvigo.esei.dai.hybridserver.HtmlController;

public class ServiceThread extends Thread {
	private Socket socket;
	private BufferedReader reader;
	private HtmlController pages;
	private PrintWriter writer;

	public ServiceThread(Socket socket,HtmlController pages) throws IOException {//Lista de controladores 2ndo argumento
		this.socket = socket;
		this.pages = pages;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(socket.getOutputStream());
	}

	public void run() {
		try (Socket socket = this.socket) {
			HTTPRequest request = new HTTPRequest(this.reader);
			Manager manager = new Manager(request,pages);
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
