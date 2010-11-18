/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.commons.util;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.util.VulpeDB4OUtil;

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
