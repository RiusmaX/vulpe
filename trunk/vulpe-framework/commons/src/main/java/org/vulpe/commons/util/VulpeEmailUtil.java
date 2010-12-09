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

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.vulpe.exception.VulpeSystemException;

/**
 * Utility class to send mail.
 */
@SuppressWarnings("unchecked")
public final class VulpeEmailUtil {

	private static final Logger LOG = Logger.getLogger(VulpeEmailUtil.class.getName());

	private static boolean isDebugEnabled = LOG.isDebugEnabled();

	/** singleton instance */
	private static VulpeEmailUtil instance = null;
	private String mailFrom = null;

	private VulpeEmailUtil() {
	}

	private VulpeEmailUtil(final String mailFrom) {
		super();
		this.mailFrom = mailFrom;
	}

	/**
	 * Returns VulpeEmailUtil instance.
	 * 
	 * @return VulpeEmailUtil instance
	 */
	public static VulpeEmailUtil getInstance() {
		if (instance == null) {
			instance = new VulpeEmailUtil();
		}
		return instance;
	}

	/**
	 * Returns VulpeEmailUtil instance.
	 * 
	 * @param mailFrom
	 *            From
	 * 
	 * @return VulpeEmailUtil instance
	 */
	public static VulpeEmailUtil getInstance(final String mailFrom) {
		if (instance == null) {
			instance = new VulpeEmailUtil(mailFrom);
		}
		return instance;
	}

	/**
	 * Send Mail to many recipients.
	 * 
	 * @param recipients
	 *            Recipients
	 * @param subject
	 *            Subject
	 * @param body
	 *            Body
	 * @throws VulpeSystemException
	 *             exception
	 */
	public void sendMail(final String[] recipients, final String subject, final String body)
			throws VulpeSystemException {
		if (!checkValidEmail(recipients)) {
			throw new VulpeSystemException("Invalid mails: " + recipients);
		}
		if (isDebugEnabled) {
			LOG.debug("Entering in sendMail...");
			for (int i = 0; i < recipients.length; i++) {
				LOG.debug("recipient: " + recipients[i]);
			}
			LOG.debug("subject: " + subject);
			LOG.debug("body: " + body);
		}
		try {
			final ResourceBundle bundle = ResourceBundle.getBundle("mail");
			if (bundle != null) {
				final HtmlEmail mail = new HtmlEmail();
				if (bundle.containsKey("mail.smtp.auth") && Boolean.valueOf(bundle.getString("mail.smtp.auth"))) {
					final String username = bundle.getString("mail.smtp.user");
					final String password = bundle.getString("mail.smtp.password");
					mail.setAuthentication(username, password);
				}
				if (bundle.containsKey("mail.from")) {
					mailFrom = bundle.getString("mail.from");
				}
				mail.setFrom(mailFrom);
				for (final String recipient : recipients) {
					mail.addTo(recipient);
				}
				mail.setHostName(bundle.getString("mail.smtp.host"));
				final String port = bundle.getString("mail.smtp.port");
				mail.setSmtpPort(Integer.valueOf(port));
				if (bundle.containsKey("mail.smtp.starttls.enable")
						&& Boolean.valueOf(bundle.getString("mail.smtp.starttls.enable"))) {
					mail.setTLS(true);
					mail.setSSL(true);
					if (bundle.containsKey("mail.smtp.socketFactory.port")) {
						String factoryPort = bundle.getString("mail.smtp.socketFactory.port");
						mail.setSslSmtpPort(factoryPort);
					}
				}
				String subjectEncode = "UTF-8";
				if (bundle.containsKey("mail.subject.encode")) {
					subjectEncode = bundle.getString("mail.subject.encode");
				}
				mail.setSubject(MimeUtility.encodeText(subject, subjectEncode, null));
				if (bundle.containsKey("mail.body.htmlSpecials.encode")
						&& Boolean.valueOf(bundle.getString("mail.body.htmlSpecials.encode"))) {
					mail.setHtmlMsg(VulpeStringUtil.encodeHTMLSpecials(body));
				} else {
					mail.setHtmlMsg(body);
				}
				mail.send();
			} else {
				throw new Exception("Send Mail properties not setted");
			}

		} catch (Exception e) {
			LOG.error("Error on send mail", e);
			throw new VulpeSystemException(e);
		}
		LOG.debug("Out of sendMail...");
	}

	/**
	 * Returns String Array of recipients.
	 * 
	 * @param recipients
	 * 
	 * @param property
	 * 
	 * @return
	 */
	public String[] getRecipients(final List recipients, final String property) {
		String[] address = new String[recipients.size()];
		for (int i = 0; i < recipients.size(); i++) {
			final Object recipient = (Object) recipients.get(i);
			try {
				if (recipient != null) {
					address[i] = BeanUtils.getProperty(recipient, property).trim();
				}
			} catch (Exception e) {
				LOG.error("Error on get email of " + recipient, e);
				address[i] = "";
			}
		}
		return address;
	}

	/**
	 * Send Mail to recipient.
	 * 
	 * @param recipient
	 * 
	 * @param subject
	 * 
	 * @param body
	 * 
	 * @throws VulpeSystemException
	 *             exception
	 */
	public void sendMail(final String recipient, final String subject, final String body) {
		LOG.debug("Entering in sendMail...");
		LOG.debug("recipient: " + recipient);
		LOG.debug("subject: " + subject);
		LOG.debug("body: " + body);
		sendMail(new String[] { recipient }, subject, body);
		LOG.debug("Out of sendMail...");
	}

	/**
	 * Send mail to recipients by Web Service.
	 * 
	 * @param recipients
	 * 
	 * @param subject
	 * 
	 * @param body
	 * 
	 * @param mailerService
	 * 
	 * @throws VulpeSystemException
	 *             exception
	 */
	public void sendMailByService(final String[] recipients, final String subject, final String body,
			final String mailerService) throws VulpeSystemException {
		try {
			final InitialContext initialContext = new InitialContext();
			final Session session = (Session) initialContext.lookup(mailerService);
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			for (String recipient : recipients) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			}
			// msg.setRecipient(Message.RecipientType.TO, new
			// InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (Exception e) {
			LOG.error(e);
		}

	}

	/**
	 * Checks if the email is valid
	 */
	public boolean checkValidEmail(final String email) {
		final StringTokenizer stringTokenizer = new StringTokenizer(email, ",");
		while (stringTokenizer.hasMoreTokens()) {
			if (!checkEmailFormat(stringTokenizer.nextToken())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifies that the emails are valid
	 */
	public boolean checkValidEmail(final String[] emails) {
		for (String email : emails) {
			if (!checkValidEmail(email)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the email format is valid
	 * 
	 * @return true if valid
	 */
	private boolean checkEmailFormat(final String email) {
		final char arroba = "@".charAt(0);
		final char dot = ".".charAt(0);
		return email == null || (email.indexOf(arroba) == -1 || email.indexOf(dot) == -1) ? false : true;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(final String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	public Properties convertResourceBundleToProperties(final ResourceBundle resource) {
		final Properties properties = new Properties();
		final Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}
		return properties;
	}

	public static void main(String[] args) {
		VulpeEmailUtil.getInstance("ibrowsebh@gmail.com").sendMail("geraldo.felipe@ibrowse.com.br", "validação",
				"validação teste");
	}
}
