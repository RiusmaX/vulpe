/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.commons.util;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.config.ConfigScope;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;
import com.db4o.io.FileStorage;

public class VulpeDB4OUtil {

	private static VulpeDB4OUtil instance = new VulpeDB4OUtil();

	public static VulpeDB4OUtil getInstance() {
		return instance;
	}

	private transient final ResourceBundle db4o = ResourceBundle
			.getBundle("db4o");

	private String databaseName;
	private String databaseDirectory;
	private int databasePort;

	private String username;
	private String password;

	private static ObjectServer objectServer;

	private static final ThreadLocal<ObjectContainer> DATABASE = new ThreadLocal<ObjectContainer>();

	/**
	 * 
	 * @return
	 */
	public ObjectContainer getObjectContainer() {
		ObjectContainer container = DATABASE.get();
		if (container == null || container.ext().isClosed()) {
			container = getObjectServer().openClient();
			DATABASE.set(container);
		}
		return container;
	}

	/**
	 *
	 */
	public void close() {
		final ObjectContainer container = DATABASE.get();
		DATABASE.set(null);
		if (container != null) {
			container.close();
		}
	}

	/**
	 *
	 */
	public void commit() {
		final ObjectContainer container = DATABASE.get();
		if (container != null) {
			container.commit();
		}
	}

	/**
	 *
	 */
	public void rollback() {
		final ObjectContainer container = DATABASE.get();
		if (container != null) {
			container.rollback();
		}
	}

	/**
	 * 
	 * @return
	 */
	public ObjectServer getObjectServer() {
		if (objectServer == null) {
			synchronized (this) {
				objectServer = getObjectServerForFilename(getDatabaseName(),
						getDatabasePort());
				// and give access
				objectServer.grantAccess(getUsername(), getPassword());
			}
		}
		return objectServer;
	}

	/**
	 *
	 */
	public void shutdown() {
		if (objectServer != null) {
			objectServer.close();
		}
	}

	/**
	 * 
	 * @param databasename
	 * @param port
	 * @return
	 */
	public ObjectServer getObjectServerForFilename(final String databasename,
			final int port) {
		final File parentDir = getDataDirectory();
		final File dbfile = new File(parentDir, databasename);
		final ServerConfiguration config = Db4oClientServer
				.newServerConfiguration();
		config.file().storage(new FileStorage());

		// for replication
		config.file().generateUUIDs(ConfigScope.GLOBALLY);
		config.file().generateVersionNumbers(ConfigScope.GLOBALLY);

		config.common().exceptionsOnNotStorable(true);
		config.common().objectClass("java.math.BigDecimal").translate(
				new com.db4o.config.TSerializable());

		return Db4oClientServer.openServer(config, dbfile.getPath(), port);
	}

	/**
	 * 
	 * @return
	 */
	private File getDataDirectory() {
		final String token = "{user.home}";
		final int pos = getDatabaseDirectory().indexOf(token);
		String directoryName = getDatabaseDirectory();
		if (getDatabaseDirectory().contains("{user.home}")) {
			directoryName = System.getProperty("user.home")
					+ getDatabaseDirectory().substring(pos + token.length(),
							getDatabaseDirectory().length());
		}
		File file = new File(directoryName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public String getDatabaseName() {
		if (StringUtils.isBlank(databaseName)) {
			databaseName = db4o.getString("databaseName");
		}
		return databaseName;
	}

	public void setDatabaseName(final String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseDirectory() {
		databaseDirectory = db4o.getString("databaseDirectory");
		return databaseDirectory;
	}

	public int getDatabasePort() {
		databasePort = Integer.valueOf(db4o.getString("databasePort"));
		return databasePort;
	}

	public String getUsername() {
		username = db4o.getString("username");
		return username;
	}

	public String getPassword() {
		password = db4o.getString("password");
		return password;
	}

	public void setDatabaseDirectory(final String databaseDirectory) {
		this.databaseDirectory = databaseDirectory;
	}

	public void setDatabasePort(final int databasePort) {
		this.databasePort = databasePort;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
