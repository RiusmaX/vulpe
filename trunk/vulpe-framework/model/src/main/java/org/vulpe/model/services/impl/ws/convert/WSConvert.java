package org.vulpe.model.services.impl.ws.convert;

/**
 * Interface básica de conversão de tipos para WebServices
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public interface WSConvert<BEAN, WSBEAN> {
	/**
	 * Converte o tipo do WS pro tipo JAVA
	 */
	BEAN toBean(WSBEAN wsBean);
	/**
	 * Converte o tipo JAVA pro tipo do WS
	 */
	WSBEAN toWSBean(BEAN bean);
}