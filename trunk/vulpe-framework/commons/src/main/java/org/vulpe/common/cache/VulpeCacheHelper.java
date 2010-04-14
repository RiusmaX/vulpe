package org.vulpe.common.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vulpe.exception.VulpeSystemException;


/**
 * Utility class to control objects in cache.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">F�bio Viana</a>
 */
@SuppressWarnings("unchecked")
public final class VulpeCacheHelper {
	private static final VulpeCacheHelper INSTANCE = new VulpeCacheHelper();

	public static VulpeCacheHelper getInstance() {
		return INSTANCE;
	}

	private VulpeCacheHelper() {
	}

	private transient final Map cache = Collections.synchronizedMap(new HashMap());

	/**
	 * M�todo que inclui um objeto no cache
	 */
	public void put(final Object key, final Object instance) {
		cache.put(key, instance);
	}

	/**
	 * M�todo que recupera o objeto no cache pela chave
	 */
	public <T> T get(final Object key) {
		return (T) cache.get(key);
	}

	/**
	 * M�todo que recupera o objeto no cache pela chave, se estiver nulo
	 * instancia
	 */
	public <T> T getInstance(final Class<T> classe) {
		if (!contains(classe)) {
			try {
				put(classe, classe.newInstance());
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return (T) get(classe);
	}

	/**
	 * M�todo que verifica se a chave est� cacheada
	 */
	public boolean contains(final Object key) {
		return cache.containsKey(key);
	}

	/**
	 * M�todo que remove o objeto do cache
	 */
	public void remove(final Object key) {
		cache.remove(key);
	}
}