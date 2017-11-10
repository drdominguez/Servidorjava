package es.uvigo.esei.dai.hybridserver;

public class Pagina {
	private String uuid;
	private String content;
	
	public Pagina(String uuid, String content) {
		this.uuid=uuid;
		this.content=content;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
