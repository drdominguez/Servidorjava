package es.uvigo.esei.dai.hybridserver.http;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HTTPResponse {
	private String version = "";
	private HTTPResponseStatus get;
	private String content = "";
	private Map<String, String> parameters = new LinkedHashMap<String, String>();
	private String putparameter = "";
	private String delparameter = "";
	private List<String> lista = new ArrayList<String>();

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
		parameters.remove(parameters.values());
	}

	public List<String> listParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public void print(Writer writer) throws IOException {

		String sol = getVersion() + " " + get.toString().substring(1) + " OK\r\n";

		if (!parameters.isEmpty()) {

			sol = sol + "Content-Type: " + parameters.get("Content-Type") + "\r\n" + "Content-Encoding: "
					+ parameters.get("Content-Encoding") + "\r\n" + "Content-Language: "
					+ parameters.get("Content-Language") + "\r\n";
		}
		if (content != "") {
			sol = sol + "Content-Length: " + content.length() + "\r\n\r\n" + content;
		}
		if (content == "") {
			sol = sol + "\r\n";
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
