package es.uvigo.esei.dai.hybridserver.http;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HTTPResponse {
	private String version = "";
	private HTTPResponseStatus get;
	private String content = "";
	private Map<String, String> parameters = new LinkedHashMap<String, String>();

	public HTTPResponse() {
		this.version = HTTPHeaders.HTTP_1_1.getHeader();
	}

	public HTTPResponseStatus getStatus() {

		return get;
	}

	public void setStatus(HTTPResponseStatus status) {
		this.get = status;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public String putParameter(String name, String value) {
		parameters.put(name, value);
		return ("Se ha insertado el nuevo parametro cuya clave es " + name + " y valor " + value);
	}

	public boolean containsParameter(String name) {
		return parameters.containsKey(name);
	}

	public String removeParameter(String name) {
		parameters.remove(name);
		return ("Se ha borrado el parametro cuya clave es " + name);
	}

	public void clearParameters() {
		parameters.clear();
	}

	public List<String> listParameters() {

		return (List<String>) parameters.values();
	}

	public void print(Writer writer) throws IOException {

		String sol = getVersion() + " " + get.toString().substring(1) + " OK\r\n";

		if (!parameters.isEmpty()) {// Si tiene parametros
			if (parameters.containsKey("Content-Type")) {// Si tiene content-type
				sol += "Content-Type: " + parameters.get("Content-Type") + "\r\n";
			}
			if (parameters.containsKey("Content-Encoding")) {
				sol += "Content-Encoding: " + parameters.get("Content-Encoding") + "\r\n";
			}
			if (parameters.containsKey("Content-Language")) {
				sol += "Content-Language: " + parameters.get("Content-Language") + "\r\n";
			}
		} // En el caso de que no tengaparametros

		if (!content.isEmpty()) {
			// content.contains(s)
			sol += "Content-Length: " + content.length() + "\r\n\r\n" + content;
		} else {
			sol += "\r\n";
		}

		CharSequence solucion = sol.subSequence(0, sol.length());
		writer.append(solucion);
		writer.flush();
	}

	@Override
	public String toString() {
		final StringWriter writer = new StringWriter();
		try {
			this.print(writer);
		} catch (IOException e) {
		}

		return writer.toString();
	}
}
