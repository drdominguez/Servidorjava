package es.uvigo.esei.dai.hybridserver;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.derby.tools.sysinfo;
import org.xml.sax.SAXException;

import es.uvigo.esei.dai.hybridserver.Launcher;
import es.uvigo.esei.dai.hybridserver.controller.FactoryControllerDB;
import es.uvigo.esei.dai.hybridserver.controller.HtmlController;
import es.uvigo.esei.dai.hybridserver.controller.XmlController;
import es.uvigo.esei.dai.hybridserver.controller.XsdController;
import es.uvigo.esei.dai.hybridserver.controller.XsltController;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequestMethod;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;
import es.uvigo.esei.dai.hybridserver.http.MIME;

public class Manager {
	private HTTPRequest request;
	private FactoryControllerDB create;
	public Manager(HTTPRequest request,FactoryControllerDB create) {//El segundo argumento hashmap
		this.request = request;
		this.create = create;
		
	}

	public HTTPResponse getResponse() {
		// Responder al cliente
		HTTPResponse response = new HTTPResponse();
		try {
		switch (request.getResourceName()) {
		// Caso de ser Vacío
		case "":
			String paginaInicial = "<head><meta charset=\"utf8\"></head>"
					+ "<DIV ALIGN=center><h1><b>Hybrid Server</b></h1></DIV>" + "<p>Alberte Pazos Martinez</p>"
					+ "<p>Daniel Rodríguez Domínguez</p>";
			String paginas = "<a href='html'>Lista de Páginas</a>  ";
			response.setContent(paginaInicial + paginas);
			response.setStatus(HTTPResponseStatus.S200);
			break;
		// Si el recurso es html
		case "html":
			MIME a=MIME.TEXT_HTML;
			response.putParameter("Content-Type", a.getMime());
			HtmlController pages=this.create.createHTMLController();
			// Comprobamos que sea un GET
			if (request.getMethod().equals(HTTPRequestMethod.GET)) {
				String uuid = request.getResourceParameters().get("uuid");
				String contenido = pages.getPage(uuid);
				// Verificar que existan parámetros
				if (!request.getResourceParameters().isEmpty()) {
					// Se comprueba que tiene un uuid
					if (uuid != null) {
						// Si teniendo uuid no tiene contenido
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
							// Sii teniendo uuid tiene contenido
						} else {
							response.setContent(contenido);
							response.setStatus(HTTPResponseStatus.S200);
						}
						// Si no tiene uuid
					} else {
						response.setStatus(HTTPResponseStatus.S404);
					}
					// Si no tiene parámetros
				} else {
					response.setStatus(HTTPResponseStatus.S200);
					response.setContent(listarPaginas(pages));
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
		case "xml":
			MIME xml=MIME.APPLICATION_XML;
			response.putParameter("Content-Type", xml.getMime());
			XmlController pagesxml=this.create.createXmlController();
			if (request.getMethod().equals(HTTPRequestMethod.GET)) {
				String uuid = request.getResourceParameters().get("uuid");
				String contenido = pagesxml.getPage(uuid);
				// Verificar que existan parámetros
				if (!request.getResourceParameters().isEmpty()) {
					// Se comprueba que tiene un uuid
					if (uuid != null) {
						// Si teniendo uuid no tiene contenido
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
							// Sii teniendo uuid tiene contenido
						} else {
							response.setContent(contenido);
							response.setStatus(HTTPResponseStatus.S200);
						}
						// Si no tiene uuid
					} else {
						response.setStatus(HTTPResponseStatus.S404);
					}
					// Si no tiene parámetros
				} else {
					response.setStatus(HTTPResponseStatus.S200);
					response.setContent(listarPaginas(pagesxml));
				}

			}
			if (request.getMethod().equals(HTTPRequestMethod.POST)) {
				String uuid = request.getHeaderParameters().get("uuid");
				if (uuid == null) {
					String solucion = request.getContent().substring(0, request.getContent().indexOf("="));
					if (!solucion.equals("xml")) {
						response.setStatus(HTTPResponseStatus.S400);
					} else {
						uuid = java.util.UUID.randomUUID().toString();
						while (uuid == request.getResourceParameters().get("uuid")) {
							uuid = java.util.UUID.randomUUID().toString();
						}
						String content = request.getContent();

						String[] contenidoigual = content.split("=");
						if (pagesxml.addPage(uuid, contenidoigual[1])) {
							String uuidHyperlink = "<a href=\"xml?uuid=" + uuid + "\">" + uuid + "</a>";
							response.setContent(uuidHyperlink);
							response.setStatus(HTTPResponseStatus.S200);
						} else {

							String uuidHyperlink = "<a href=\"xml?uuid=" + uuid + "\">" + uuid + "</a>";
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
				String contenido = pagesxml.getPage(uuid);
				if (uuid == null) {
					response.setStatus(HTTPResponseStatus.S400);
				} else {
					if (contenido == null) {
						pagesxml.deletePage(uuid);
						response.setStatus(HTTPResponseStatus.S200);
					} else {
						if (pagesxml.deletePage(uuid)) {
							response.setStatus(HTTPResponseStatus.S200);
						} else {
							response.setStatus(HTTPResponseStatus.S404);
						}
					}
				}
			}

			break;
		case "xsd":
			MIME xsd=MIME.APPLICATION_XML;
			response.putParameter("Content-Type", xsd.getMime());
			XsdController pagesxsd=this.create.createXsdController();
			if (request.getMethod().equals(HTTPRequestMethod.GET)) {
				String uuid = request.getResourceParameters().get("uuid");
				String contenido = pagesxsd.getPage(uuid);
				// Verificar que existan parámetros
				if (!request.getResourceParameters().isEmpty()) {
					// Se comprueba que tiene un uuid
					if (uuid != null) {
						// Si teniendo uuid no tiene contenido
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
							// Sii teniendo uuid tiene contenido
						} else {
							response.setContent(contenido);
							response.setStatus(HTTPResponseStatus.S200);
						}
						// Si no tiene uuid
					} else {
						response.setStatus(HTTPResponseStatus.S404);
					}
					// Si no tiene parámetros
				} else {
					response.setStatus(HTTPResponseStatus.S200);
					response.setContent(listarPaginas(pagesxsd));
				}

			}
			if (request.getMethod().equals(HTTPRequestMethod.POST)) {
				String uuid = request.getHeaderParameters().get("uuid");
				if (uuid == null) {
					String solucion = request.getContent().substring(0, request.getContent().indexOf("="));
					if (!solucion.equals("xsd")) {
						response.setStatus(HTTPResponseStatus.S400);
					} else {
						uuid = java.util.UUID.randomUUID().toString();
						while (uuid == request.getResourceParameters().get("uuid")) {
							uuid = java.util.UUID.randomUUID().toString();
						}
						String content = request.getContent();

						String[] contenidoigual = content.split("=");
						if (pagesxsd.addPage(uuid, contenidoigual[1])) {
							String uuidHyperlink = "<a href=\"xsd?uuid=" + uuid + "\">" + uuid + "</a>";
							response.setContent(uuidHyperlink);
							response.setStatus(HTTPResponseStatus.S200);
						} else {

							String uuidHyperlink = "<a href=\"xsd?uuid=" + uuid + "\">" + uuid + "</a>";
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
				String contenido = pagesxsd.getPage(uuid);
				if (uuid == null) {
					response.setStatus(HTTPResponseStatus.S400);
				} else {
					if (contenido == null) {
						pagesxsd.deletePage(uuid);
						response.setStatus(HTTPResponseStatus.S200);
					} else {
						if (pagesxsd.deletePage(uuid)) {
							response.setStatus(HTTPResponseStatus.S200);
						} else {
							response.setStatus(HTTPResponseStatus.S404);
						}
					}
				}
			}

			break;
		case "xslt":
			MIME xslt=MIME.APPLICATION_XML;
			response.putParameter("Content-Type", xslt.getMime());
			XsltController pagesxslt=this.create.createXsltController();
			if(pagesxslt==null) {
				response.setStatus(HTTPResponseStatus.S500);
			}else {
			if (request.getMethod().equals(HTTPRequestMethod.GET)) {
				String uuid = request.getResourceParameters().get("uuid");
				String contenido = pagesxslt.getPage(uuid);
							
				if (!request.getResourceParameters().isEmpty()) {
					// Se comprueba que tiene un uuid
					if (uuid != null) {
						// Si teniendo uuid no tiene contenido
						if (contenido == null) {
							response.setStatus(HTTPResponseStatus.S404);
							// Sii teniendo uuid tiene contenido
						} else {
							response.setContent(contenido);
							response.setStatus(HTTPResponseStatus.S200);
						}
						// Si no tiene uuid
					} else {
						response.setStatus(HTTPResponseStatus.S404);
					}
					// Si no tiene parámetros
				} else {
					response.setStatus(HTTPResponseStatus.S200);
					response.setContent(listarPaginas(pagesxslt));
				}

			}
			if (request.getMethod().equals(HTTPRequestMethod.POST)) {
				if(request.getResourceParameters().containsKey("xslt")&& request.getResourceParameters().containsKey("xsd")) {//AQUÍ COMPRUEBO SI LA REQUEST TIENE XSLT Y XSD
					if(request.getResourceParameters().get("xsd")!=null) {//AQUI COMPRUEBO SI ME HAN PASADO UN XSD QUE NO ES NULL
						XsdController xsldexist=create.createXsdController();
						String xsdl=request.getResourceParameters().get("xsd");
						if(xsldexist.exist(xsdl)) {//AQUÍ COMPRUEBO SI EXISTE EL XSD EN LA BD
						String content =request.getContent();
						String uuid=java.util.UUID.randomUUID().toString();
						response.setStatus(HTTPResponseStatus.S200);
						response.putParameter("xsd", xsdl);
						String []contenido =content.split("=");//EN CONTENIDO TENEMOS TAMBIEN EL XSD Y SOLO DEBERÍAMOS TENER EL CONTENIDO DEL XSLT TENEMOS ALGO DEL ESTILO XSD=JCDBHUVCW XSLT=????????????
						content=contenido[2];//AQUI COGEMOS SOLO EL CONTENIDO DEL XSLT QUE ESTA EN LA SEGUNDA POSICION DEL ARRAY
						pagesxslt.addPage(uuid, content, xsdl);
						response.putParameter("xslt", content);
						String uuidHyperlink = "<a href=\"xslt?uuid=" + uuid + "\">" + uuid + "</a>";
						response.setContent(uuidHyperlink);
						}else
						response.setStatus(HTTPResponseStatus.S404);
					}else
					response.setStatus(HTTPResponseStatus.S404);
				}else
				response.setStatus(HTTPResponseStatus.S400);
			}
			
			if (request.getMethod().equals(HTTPRequestMethod.DELETE)) {
				String uuid = request.getResourceParameters().get("uuid");
				String contenido = pagesxslt.getPage(uuid);
				if (uuid == null) {
					response.setStatus(HTTPResponseStatus.S400);
				} else {
					if (contenido == null) {
						pagesxslt.deletePage(uuid);
						response.setStatus(HTTPResponseStatus.S200);
					} else {
						if (pagesxslt.deletePage(uuid)) {
							response.setStatus(HTTPResponseStatus.S200);
						} else {
							response.setStatus(HTTPResponseStatus.S404);
						}
					}
				}
			}
			}
			break;
		default:
			// Bad request
			response.setStatus(HTTPResponseStatus.S400);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
}

private String listarPaginas(HtmlController pages) throws Exception {
	List<String> paginas = pages.listPages();
	Iterator<String> it = paginas.iterator();
	String identificador = "<head><meta></head>"+"<h1><b>Servidor Local</b></h1>";
	if (!paginas.isEmpty()) {
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
	return identificador;
}
private String listarPaginas(XmlController pages) throws Exception {
	List<String> paginas = pages.listPages();
	Iterator<String> it = paginas.iterator();
	String identificador = "<head><meta></head>"+"<h1><b>Servidor Local</b></h1>";
	if (!paginas.isEmpty()) {
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
	return identificador;
}
private String listarPaginas(XsdController pages) throws Exception {
	List<String> paginas = pages.listPages();
	Iterator<String> it = paginas.iterator();
	String identificador = "<head><meta></head>"+"<h1><b>Servidor Local</b></h1>";
	if (!paginas.isEmpty()) {
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
	return identificador;
}
private String listarPaginas(XsltController pages) throws Exception {
	List<String> paginas = pages.listPages();
	Iterator<String> it = paginas.iterator();
	String identificador = "<head><meta></head>"+"<h1><b>Servidor Local</b></h1>";
	if (!paginas.isEmpty()) {
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
	return identificador;
}

}
