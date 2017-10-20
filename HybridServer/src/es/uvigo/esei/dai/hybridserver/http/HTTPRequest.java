package es.uvigo.esei.dai.hybridserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HTTPRequest {
	private Reader reader;
	private HTTPRequestMethod get;
	private String chain;
	private String[] path;
	private String name;
	private Map<String, String> mapa = new LinkedHashMap<String, String>();
	private String version;
	private Map<String, String> mapacontiene = new LinkedHashMap<String, String>();
	int length = 0;
	private String content;

	public HTTPRequest(Reader reader) throws IOException, HTTPParseException {
		this.reader = reader;
		BufferedReader bufferedreader = new BufferedReader(this.reader);

		// Recorremos por lineas
		String linea;
		linea = bufferedreader.readLine();

		// Buscamos getMethod()
		String[] espacio = (linea.split(" "));
		if (espacio[0].equals("")) {
			throw new HTTPParseException();
		}

		if (!espacio[0].equals("GET") && !espacio[0].equals("PUT") && !espacio[0].equals("HEAD")
				&& !espacio[0].equals("TRACE") && !espacio[0].equals("POST") && !espacio[0].equals("OPTIONS")
				&& !espacio[0].equals("CONNECT") && !espacio[0].equals("DELETE")) {
			throw new HTTPParseException();
		}
		get = HTTPRequestMethod.valueOf(espacio[0]);
		if(espacio.length>2)
		chain = espacio[1];
		else if(espacio.length>1 &&!espacio[1].equals("HTTP/1.1")) {
			chain=espacio[1];
		}
		else {
		chain="";
		}
		
		
		
		
		
		//Manera de sacar el nombre
		// String[] interrogantearray = chain.split("?");
		// name = interrogantearray[0];
		
		
		
		
		
		
		
		String path1;

		int interrogante = chain.indexOf("?");// posicion donde esta el interrogante
		// Mira si no hay un interrogante, si no lo hay ponemos el path y el nombre es
		// igual a lo que contiene chain menos la primera barra
		
		
		if (interrogante == -1) {
			if(chain.length()<2) {
				path1="";
			}
			else {
			 path1= chain.substring(1);
			}
			name = path1;
			int path2 = path1.indexOf("/");

			if (path2 != -1) {//
				path = new String[] { path1.substring(0, path2).toString(), path1.substring(path2 + 1).toString() };
			} else if (name.equals("") && (path2 == -1)) {
				path = new String[0];
			} else if (!name.equals("") && (path2 == -1)) {
				path = new String[] { name.toString() };
			}

		} else {
			String antes = chain.substring(0, interrogante);// subcadena desde la posicion 0 hasta el interrogante
			String[] barra = antes.split("/");
			String[] espacio2 = (antes.split(" "));
			name = chain.substring(1,interrogante);
			if (barra.length > 2) {
				path = new String[] { barra[1].toString(), barra[2].toString() };
			} else
				path = new String[] { barra[1].toString() };

			
			String cadena = chain.substring(interrogante+1);
			
			 
			String[] cadenas = (cadena.split("&"));

			// Obtenemos los valores a introducir en el mapa
			int igual = cadenas[0].indexOf("=");// posicion donde esta el interrogante
			String cadena1 = cadenas[0].substring(0, igual);
			String cadena2 = cadenas[0].substring(igual + 1);
			
			if (cadenas.length > 1) {
				igual = cadenas[1].indexOf("=");// posicion donde esta el interrogante
				if (igual > -1) {
					String cadena3 = cadenas[1].substring(0, igual);
					String cadena4 = cadenas[1].substring(igual + 1);
					igual = cadenas[2].indexOf("=");// posicion donde esta el interrogante
					String cadena5 = cadenas[2].substring(0, igual);
					String cadena6 = cadenas[2].substring(igual + 1);
					
					// Introducimos los valores en el mapa
					mapa.put(cadena1, cadena2);
					mapa.put(cadena3, cadena4);
					mapa.put(cadena5, cadena6);
				}
			}
		}
		// Buscamos la versión

		if (espacio.length > 2) {
			String string = espacio[2];
			version = string;
		} 
		else
			throw new HTTPParseException();
		int puntos;
		if (linea != "") {
			while (!(linea = bufferedreader.readLine()).equals("")) {
				if(linea.equals(null)) {
					throw new HTTPParseException();
				}
				puntos = linea.indexOf(":");
				// Comprobamos si encontramos la dirección del host
				if (puntos == -1) {
					throw new HTTPParseException();
				}
				String cadena7 = linea.substring(0, puntos);
				String cadena8 = linea.substring(puntos + 2);
				mapacontiene.put(cadena7, cadena8);
			}
			String tamaño;
			// Calculamos length y contentlength
			if (mapacontiene.containsKey("Content-Length")) {
				tamaño = mapacontiene.get("Content-Length");
				length = Integer.parseInt(tamaño);
				char[] caracteres = new char[length];
				bufferedreader.read(caracteres);
				content = new String(caracteres);

				String type = mapacontiene.get("Content-Type");
				if (type != null && type.startsWith("application/x-www-form-urlencoded")) {
					content = URLDecoder.decode(content, "UTF-8");
				}
				// Buscamos ResourcePArameters

				int posicionamp;
				String respuesta;
				String queda;
				int amp2;
				respuesta = content;
				int igual = 0;
				while ((igual = respuesta.indexOf("=")) != -1) {
					posicionamp = respuesta.indexOf("&");
					if (posicionamp == -1) {
						mapa.put(respuesta.substring(0, igual), respuesta.substring(igual + 1));
						respuesta = "Se acabo";
					} else {

						mapa.put(respuesta.substring(0, igual), respuesta.substring(igual + 1, posicionamp));
						respuesta = respuesta.substring(posicionamp + 1);
					}

				}

			}

		}
	}

	public HTTPRequestMethod getMethod() {

		return get;
	}

	public String getResourceChain() {

		return chain;
	}

	public String[] getResourcePath() {
		return path;
	}

	public String getResourceName() {
		return name;
	}

	public Map<String, String> getResourceParameters() {
		return mapa;
	}

	public String getHttpVersion() {
		return version;
	}

	public Map<String, String> getHeaderParameters() {
		return mapacontiene;
	}

	public String getContent() {
		return content;
	}

	public int getContentLength() {
		return length;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getMethod().name()).append(' ').append(this.getResourceChain())
				.append(' ').append(this.getHttpVersion()).append("\r\n");

		for (Map.Entry<String, String> param : this.getHeaderParameters().entrySet()) {
			sb.append(param.getKey()).append(": ").append(param.getValue()).append("\r\n");
		}

		if (this.getContentLength() > 0) {
			sb.append("\r\n").append(this.getContent());
		}

		return sb.toString();
	}
}
