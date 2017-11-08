package es.uvigo.esei.dai.hybridserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequestMethod;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class ServiceThread extends Thread {
	private Socket socket;
	private HTMLDAO pages;
	private BufferedReader reader;
	private PrintWriter writer;

	public ServiceThread(Socket socket, HTMLDAO pages) throws IOException {
		this.socket = socket;
		this.pages = pages;
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
				response.setContent("Hybrid Server + Alberte Pazos Martinez y Daniel Rodríguez Domínguez");
				break;
			case "html":
				System.out.println("html");
				if (request.getMethod().equals(HTTPRequestMethod.GET)) {
					String uuid = request.getResourceParameters().get("uuid");
					String contenido = pages.getPage(uuid);
					if (uuid != null) {
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
						} else {
							response.setContent(contenido);
							response.setStatus(HTTPResponseStatus.S200);
						}
					} else {
						response.setContent("<a href=\"html?uuid=" + pages.listPages().toString() + "\">"
								+ pages.listPages().toString() + "</a>");

						response.setStatus(HTTPResponseStatus.S200);
					}

				}
				if (request.getMethod().equals(HTTPRequestMethod.POST)) {
					String uuid = request.getHeaderParameters().get("uuid");
					if (uuid == null) {
						String solucion = request.getContent().substring(0, request.getContent().indexOf("="));
						if (!solucion.equals("html")) {
							response.setStatus(HTTPResponseStatus.S400);
						} else {
							uuid = java.util.UUID.randomUUID().toString();
							while (uuid == request.getResourceParameters().get("uuid")) {
								uuid = java.util.UUID.randomUUID().toString();
							}
							String content = request.getContent();

							String[] contenidoigual = content.split("=");
							if (pages.addPage(uuid, contenidoigual[1])) {
								String uuidHyperlink = "<a href=\"html?uuid=" + uuid + "\">" + uuid + "</a>";
								response.setContent(uuidHyperlink);
								response.setStatus(HTTPResponseStatus.S200);
							} else {

								String uuidHyperlink = "<a href=\"html?uuid=" + uuid + "\">" + uuid + "</a>";
								response.setContent(uuidHyperlink);
								response.setStatus(HTTPResponseStatus.S200);
							}
						}
					} else {
						response.setStatus(HTTPResponseStatus.S200);
					}
				}
				if (request.getMethod().equals(HTTPRequestMethod.DELETE)) {
					String uuid = request.getResourceParameters().get("uuid");
					String contenido = pages.getPage(uuid);
					if (uuid == null) {
						response.setStatus(HTTPResponseStatus.S200);
					} else {
						if (contenido == null) {
							pages.deletePage(uuid);
							response.setStatus(HTTPResponseStatus.S200);
						} else {
							if (pages.deletePage(uuid)) {
								response.setStatus(HTTPResponseStatus.S200);
							} else {
								response.setStatus(HTTPResponseStatus.S404);
							}
						}
					}
				}

				break;
			default:
				// Bad request
				System.out.println("default");
				response.setStatus(HTTPResponseStatus.S400);
			}

			response.print(this.writer);
		} catch (IOException | HTTPParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
