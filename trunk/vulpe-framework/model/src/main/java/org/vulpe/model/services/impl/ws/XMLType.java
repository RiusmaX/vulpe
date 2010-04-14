package org.vulpe.model.services.impl.ws;

/**
 * Bean utilizado para o auxilio na conversão de tipos do WebServices
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public class XMLType {
	private String type;
	private String typeClass;

	public XMLType(final String type, final String typeClass){
		this.type = type;
		this.typeClass = typeClass;
	}

	public String getType() {
		return type;
	}
	public void setType(final String type) {
		this.type = type;
	}
	public String getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(final String typeClass) {
		this.typeClass = typeClass;
	}
}