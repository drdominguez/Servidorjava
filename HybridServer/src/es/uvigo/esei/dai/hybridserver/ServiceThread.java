package es.uvigo.esei.dai.hybridserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequestMethod;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class ServiceThread extends Thread {
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public ServiceThread(Socket socket) throws IOException {
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(socket.getOutputStream());
	}

	public void run() {
		try (Socket socket = this.socket) {
			HTTPRequest request = new HTTPRequest(this.reader);
			
			// Responder al cliente
			HTTPResponse response = new HTTPResponse();
			
			switch (request.getResourceName()) {
			case "":
				response.setStatus(HTTPResponseStatus.S200);
				response.setContent("Hybrid Server + number");
				break;
			case "html":
				if(request.getMethod().equals(HTTPRequestMethod.GET)) {
					String uuid=request.getResourceParameters().get("uuid");
					String contenido = HybridServer.pages.getPage(uuid);
					if(uuid != null) {
						if(contenido==null) {
							response.setStatus(HTTPResponseStatus.S404);
						}else {
							response.setStatus(HTTPResponseStatus.S200);
							response.setContent(contenido);
						}
					}else {
						response.setStatus(HTTPResponseStatus.S200);
						response.setContent(HybridServer.pages.getPage(contenido));
					}
					
				}
				if(request.getMethod().equals(HTTPRequestMethod.POST)) {
					response.setStatus(HTTPResponseStatus.S201);
					response.setContent("");
				}
				if(request.getMethod().equals(HTTPRequestMethod.DELETE)) {
					response.setStatus(HTTPResponseStatus.S200);
					response.setContent("");
				}
				
				break;
			default:
				// Bad request
				response.setStatus(HTTPResponseStatus.S400);
			}

			response.print(this.writer);
		} catch (IOException | HTTPParseException e) {
			e.printStackTrace();
		}
	}

}
