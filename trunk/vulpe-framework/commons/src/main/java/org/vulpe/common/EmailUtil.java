package org.vulpe.common;

import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.vulpe.exception.VulpeSystemException;

/**
 * Utility class to send mail.
 */
public final class EmailUtil {

	private static final Logger LOG = Logger.getLogger(EmailUtil.class
			.getName());

	private static boolean isDebugEnabled = LOG.isDebugEnabled();

	/** singleton instance */
	private static EmailUtil instance = null;
	private String mailServer = null;
	private String mailFrom = null;
	private String env = null;

	private EmailUtil() {
	}

	private EmailUtil(final String mailServer, final String mailFrom,
			final String env) {
		super();
		this.mailServer = mailServer;
		this.mailFrom = mailFrom;
		this.setEnv(env);
	}

	/**
	 * Returns EmailUtil instance.
	 *
	 * @param mailServer
	 *            Mail Server
	 * @param mailFrom
	 *            From
	 * @param env
	 *            "prod" or "test"
	 *
	 * @return EmailUtil instance
	 */
	public static EmailUtil getInstance(final String mailServer,
			final String mailFrom, final String env) {
		if (instance == null) {
			instance = new EmailUtil(mailServer, mailFrom, env);
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
	public void sendMail(final String[] recipients, final String subject,
			final String body) throws VulpeSystemException {

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

			if ("prod".equals(getEnv())) {
				final Properties properties = new Properties();
				properties.put("mail.smtp.host", mailServer);
				final Session session = Session.getDefaultInstance(properties,
						null);
				final MimeMessage mimemessage = new MimeMessage(session);
				final InternetAddress internetaddress = new InternetAddress(
						mailFrom);
				mimemessage.setFrom(internetaddress);
				final InternetAddress ainternetaddress[] = getAddress(recipients);
				mimemessage.setRecipients(javax.mail.Message.RecipientType.TO,
						ainternetaddress);
				// mimemessage.setRecipients(javax.mail.Message.RecipientType.CC,
				// ainternetaddress);

				final String assunto = MimeUtility.encodeText(subject, "UTF-8",
						null);
				mimemessage.setSubject(assunto);
				mimemessage.setText(body);
				mimemessage.addHeader("Content-type", "text/html");
				mimemessage.setHeader("X-Mailer", "MailerInstance");
				Transport.send(mimemessage);
			} else if ("test".equals(getEnv())) {
				printMail(recipients, subject, body);
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
	 * Print email
	 *
	 * @param recipients
	 *
	 * @param subject
	 *
	 * @param body
	 *
	 */
	public void printMail(final String[] recipients, final String subject,
			final String body) {
		LOG.info("Email enviado para: ");
		for (int i = 0; i < recipients.length; i++) {
			LOG.info(recipients[i].trim() + " - ");
		}
		LOG.info("Subject:");
		LOG.info(subject);
		LOG.info("Conteúdo: ");
		LOG.info(body);
		LOG.info("-----------------------------");
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
	@SuppressWarnings("unchecked")
	public String[] getRecipients(final List recipients, final String property) {
		String[] address = new String[recipients.size()];
		for (int i = 0; i < recipients.size(); i++) {
			final Object recipient = (Object) recipients.get(i);
			try {
				if (recipient != null) {
					address[i] = BeanUtils.getProperty(recipient, property)
							.trim();
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
	public void sendMail(final String recipient, final String subject,
			final String body) {
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
	public void sendMailByService(final String[] recipients,
			final String subject, final String body, final String mailerService)
			throws VulpeSystemException {
		try {
			final InitialContext initialContext = new InitialContext();
			final Session session = (Session) initialContext
					.lookup(mailerService);

			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));

			for (String recipient : recipients) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(recipient));
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
	 * Converte um array de String de endereços de e-mail em array de
	 * InternetAddress.
	 *
	 * @param address
	 *            array de endereços (do tipo String)
	 * @return array de InternetAddress
	 * @throws AddressException
	 *             Erro ao converter uma String de endereço para InternetAddress
	 */
	private InternetAddress[] getAddress(final String[] address)
			throws AddressException {

		InternetAddress[] iAddresses = new InternetAddress[address.length];

		for (int i = 0; i < address.length; i++) {
			try {
				if (address[i].length() > 0) {
					iAddresses[i] = new InternetAddress(address[i]);
				}
			} catch (Exception e) {
				LOG.error("Erro ao obter endereco de: " + address[i]);
			}
		}
		return iAddresses;
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
		final char arroba = "@".charAt(0); // NOPMD by felipe on 16/03/10 16:51
		final char dot = ".".charAt(0); // NOPMD by felipe on 16/03/10 16:52
		return email == null
				|| (email.indexOf(arroba) == -1 || email.indexOf(dot) == -1) ? false
				: true;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(final String mailServer) {
		this.mailServer = mailServer;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(final String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setEnv(final String env) {
		this.env = env;
	}

	public String getEnv() {
		return env;
	}

}
