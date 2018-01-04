package es.uvigo.esei.dai.hybridserver.http;

public enum MIME {
	APPLICATION_XML("application/xml"), FORM("application/x-www-form-urlencoded"), TEXT_HTML("text/html");

	private String mime;

	private MIME(String mime) {
		this.mime = mime;
	}

	public String getMime() {
		return mime;
	}
}