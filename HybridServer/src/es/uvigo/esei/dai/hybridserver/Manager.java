package es.uvigo.esei.dai.hybridserver;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequestMethod;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class Manager {
	private HTTPRequest request;
	private HtmlController pages;

	public Manager(HTTPRequest request,HtmlController pages) {//El segundo argumento hashmap
		this.request = request;
		this.pages = pages;
	}

	public HTTPResponse getResponse() {
		// Responder al cliente
		HTTPResponse response = new HTTPResponse();
		try {
		switch (request.getResourceName()) {
		// Caso de ser Vacío
		case "":
			System.out.println("vacía");
			String paginaInicial = "<head><meta charset=\"utf8\"></head>"
					+ "<DIV ALIGN=center><h1><b>Hybrid Server</b></h1></DIV>" + "<p>Alberte Pazos Martinez</p>"
					+ "<p>Daniel Rodríguez Domínguez</p>";
			String paginas = "<a href='html'>Lista de Páginas</a>  ";
			response.setContent(paginaInicial + paginas);
			response.setStatus(HTTPResponseStatus.S200);
			break;
		// Si el recurso es html
		case "html":
			System.out.println("html");
			// Comprobamos que sea un GET
			if (request.getMethod().equals(HTTPRequestMethod.GET)) {
				String uuid = request.getResourceParameters().get("uuid");
				String contenido = pages.getPage(uuid);
				// Verificar que existan parámetros
				if (!request.getResourceParameters().isEmpty()) {
					// Se comprueba que tiene un uuid
					if (uuid != null) {
						System.out.println("Tiene uuid");
						// Si teniendo uuid no tiene contenido
						if (contenido == null) {
							System.out.println("Tiene uuid pero no tiene contenido");
							response.setStatus(HTTPResponseStatus.S404);
							// Sii teniendo uuid tiene contenido
						} else {
							System.out.println("Tiene contenido y uuid");
							response.setContent(contenido);
							response.setStatus(HTTPResponseStatus.S200);
						}
						// Si no tiene uuid
					} else {
						System.out.println("No tiene uuid");
						response.setStatus(HTTPResponseStatus.S404);
					}
					// Si no tiene parámetros
				} else {
					response.setStatus(HTTPResponseStatus.S200);
					System.out.println("No tiene parametros");
					response.setContent(listarPaginas());
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
					response.setStatus(HTTPResponseStatus.S400);
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
}

private String listarPaginas() throws Exception {
	System.out.println("Entra150");
	List<String> paginas = pages.listPages();
	System.out.println("152");
	Iterator<String> it = paginas.iterator();
	String identificador = "<head><meta charset=\\\"utf8\\\"></head>"+"<h1><b>Servidor Local</b></h1>";
	System.out.println("Entra154");
	if (!paginas.isEmpty()) {
		System.out.println("Entra156");
		identificador += "<ul>";
		while (it.hasNext()) {
			String itpagina = it.next();
			identificador += "<li><a href='html?uuid=" + itpagina + "'>" + itpagina
					+ "</a></li>";
		}
		identificador += "</ul>";
	} else {
		identificador += "El servidor esta vacío";
	}
	System.out.println("Identificador: "+identificador);
	return identificador;
}

}
