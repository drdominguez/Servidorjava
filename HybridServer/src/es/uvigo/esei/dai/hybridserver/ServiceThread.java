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
				if (request.getMethod().equals(HTTPRequestMethod.GET)) {
					String uuid = request.getResourceParameters().get("uuid");

					String contenido = HybridServer.pages.getPage(uuid);
					if (uuid != null) {
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
						} else {
							response.setStatus(HTTPResponseStatus.S200);
							response.setContent(contenido);
						}
					} else {

						response.setStatus(HTTPResponseStatus.S200);
						response.setContent(HybridServer.pages.listPages().toString());

					}

				}
				if (request.getMethod().equals(HTTPRequestMethod.POST)) {
					String uuid = request.getHeaderParameters().get("uuid");
					String contenido = HybridServer.pages.getPage(uuid);
					if (uuid == null) {
						String solucion = request.getContent().substring(0, request.getContent().indexOf("="));
						if (!solucion.equals("html")) {
							response.setStatus(HTTPResponseStatus.S400);
						} else {
							uuid = java.util.UUID.randomUUID().toString();
							while(uuid == request.getResourceParameters().get("uuid")) {
								uuid = java.util.UUID.randomUUID().toString();
								System.out.println("Mismo uuid");
							}
							String content =request.getContent();
							String[] contenidoigual = content.split("=");
							HybridServer.pages.addPage(uuid, contenidoigual[1]);
							String uuidHyperlink = "<a href=\"html?uuid=" + uuid + "\">" + uuid + "</a>";
							response.setContent(uuidHyperlink);
							//System.out.println("Soilucion: " + request.getContent());
							response.setStatus(HTTPResponseStatus.S200);

						}

					} //else {
//						if (contenido.equals(uuid)) {
//							System.out.println("******");
//							response.setStatus(HTTPResponseStatus.S404);
//						} else
//							System.out.println("!!!!!!!!!!");
//							response.setStatus(HTTPResponseStatus.S201);
//					}
				}
				if (request.getMethod().equals(HTTPRequestMethod.DELETE)) {
					String uuid = request.getResourceParameters().get("uuid");
					String contenido = HybridServer.pages.getPage(uuid);
					if (uuid == null) {
						response.setStatus(HTTPResponseStatus.S200);
					} else {
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
						} else {
							if (HybridServer.pages.deletePage(uuid)) {
								response.setStatus(HTTPResponseStatus.S200);
							} else
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
