package es.uvigo.esei.dai.hybridserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
							response.setContent(HybridServer.pages.listPages().toString());
							System.out.println(HybridServer.pages.listPages().toString());
							//HybridServer.pages.
							
						
					}
					
				}
				if(request.getMethod().equals(HTTPRequestMethod.POST)) {
					System.out.println("1");
					String uuid=request.getHeaderParameters().get("uuid");
					System.out.println("2");
					String contenido=HybridServer.pages.getPage(uuid);
					if(uuid==null) {
						
						String solucion=request.getContent().substring(0, request.getContent().indexOf("="));
						if (!solucion.equals("html")) {
							System.out.println(solucion);
						System.out.println("3");
					response.setStatus(HTTPResponseStatus.S400);
						}else {
							System.out.println("3");
							response.setStatus(HTTPResponseStatus.S200);
							//Aqui tienes que crear un uuid para la nueva pagina y listo
							HybridServer.pages.addPage(uuid, request.getContent());
						}
					
					}
					else {
						if(contenido.equals(uuid)) {
							System.out.println(contenido);
							response.setStatus(HTTPResponseStatus.S404);
						}
						else
						response.setStatus(HTTPResponseStatus.S201);
					}
				}
				if(request.getMethod().equals(HTTPRequestMethod.DELETE)) {
					String uuid=request.getResourceParameters().get("uuid");
					String contenido=HybridServer.pages.getPage(uuid);
					if(uuid==null) {
						System.out.println("entro aquiiiiiiiiiiiiiiiiii");
						response.setStatus(HTTPResponseStatus.S200);
					}else {
						if(contenido==null) {
						response.setStatus(HTTPResponseStatus.S404);
						}else {
							System.out.println(uuid);
							
							if(HybridServer.pages.deletePage(uuid)) {
								response.setStatus(HTTPResponseStatus.S200);
							}
							else
								response.setStatus(HTTPResponseStatus.S404);
						}
					}
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
