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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;

/**
 * Utility class to date format.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public final class VulpeDateUtil {

	private static final Logger LOG = Logger.getLogger(VulpeDateUtil.class);

	public static final String HHMM = "HH:mm";
	public static final String HHMMSS = "HH:mm:ss";

	/** Date format: dd/MM/yyyy. */
	public static final String DDMMYYYY = "dd/MM/yyyy";

	/** Date format: ddMMyyyy. */
	public static final String DDMMYYNB = "ddMMyy";

	/** Date format: dd/MM/yyyy HH:mm:ss. */
	public static final String DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";

	/** Date format: dd/MM/yyyy HH:mm. */
	public static final String DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";

	/** Date format: dd/MM/yyyy HH:mm:ss,SSSSSS . */
	public static final String DATE_TIME_FULL = "dd/MM/yyyy HH:mm:ss.SSSSS";

	/** Date format: dd-MM-yyyy hh:mm:ss . */
	public static final String DATE_TIME_AMPM = "dd-MM-yyyy hh:mm:ss";

	private VulpeDateUtil() {

	}

	/**
	 * Retrieves current date.
	 *
	 * @author <a href="mailto:smendes@cit.com.br">Silvio Mendes</a>
	 * @since 12/04/2006
	 * @return VulpeDate Data corrente
	 */
	public static Date getCurrentDate() {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.getTime();
	}

	/**
	 * Retrieves current date plus one millisecond more.
	 *
	 * @return
	 */
	public static Date getCurrentDatePlusOne() {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis() + 1);
		return calendar.getTime();
	}

	/**
	 * Retrieves current date decrease one millisecond.
	 *
	 * @return
	 */
	public static Date getCurrentDateMinusOne() {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis() - 1);
		return calendar.getTime();
	}

	/**
	 * Retrieves yesterday date.
	 *
	 * @return
	 */
	public static Date getYesterdayDate() {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis() - 86400000);
		return calendar.getTime();
	}

	/**
	 * Retrieves current date on TimeStamp format: ddmmaahhmmss.
	 */
	public static String getCurrentTimeStamp() {
		return getTimeStamp(System.currentTimeMillis());
	}

	/**
	 * Retrieves date on TimeStamp format: ddmmaahhmmss.
	 *
	 * @param timestamp
	 * @return
	 */
	public static String getTimeStamp(final long timestamp) {

		final Calendar now = new GregorianCalendar();
		now.setTimeInMillis(timestamp);

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
				VulpeConstants.PORTUGUESE_LOCALE);
		final String ddmmaahhmmss = sdf.format(now.getTime());

		if (LOG.isDebugEnabled()) {
			LOG.debug("Data no formato ddmmaahhmmss: " + ddmmaahhmmss);
		}

		return ddmmaahhmmss;
	}

	/**
	 * Formata a data no formato padrão da aplicação (como sugerido no guia de
	 * espeficicação suplementar).
	 */
	public static String getDateFormated(final Date data) {
		final SimpleDateFormat sdf = new SimpleDateFormat(VulpeDateUtil.DDMMYYYY,
				VulpeConstants.PORTUGUESE_LOCALE);
		return sdf.format(data);
	}

	/**
	 * Formata a data no formato ddmmYY sem barra.
	 */
	public static String getDateFormattedNoBar(final Date data) {
		final SimpleDateFormat sdf = new SimpleDateFormat(VulpeDateUtil.DDMMYYNB,
				VulpeConstants.PORTUGUESE_LOCALE);
		return sdf.format(data);
	}

	/**
	 * Formata a data e hora no formato padrão da aplicação (como sugerido no
	 * guia de espeficicação suplementar).
	 */
	public static String getDateTimeFormatted(final Date data) {
		// cria o formato pelo pattern
		final SimpleDateFormat sdf = new SimpleDateFormat(VulpeDateUtil.DDMMYYYYHHMMSS,
				VulpeConstants.PORTUGUESE_LOCALE);
		return sdf.format(data);
	}

	/**
	 * Obter o pattern de data especificado.
	 */
	public static Date getDate(final String date, final String pattern) {
		Date returnDate = null;

		// cria o formato pelo pattern
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern, VulpeConstants.PORTUGUESE_LOCALE);
		try {
			returnDate = sdf.parse(date);
		} catch (Exception e) {
			LOG.debug("Data com formato incorreto: " + pattern);
		}

		return returnDate;
	}

	/**
	 * Retorna a data formatada com o patter especificado.
	 */
	public static String getDate(final Date date, final String pattern) {
		String returnDate = null;

		// cria o formato pelo pattern
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern, VulpeConstants.PORTUGUESE_LOCALE);
		try {
			returnDate = sdf.format(date);
		} catch (Exception e) {
			LOG.debug("Data com formato incorreto: " + pattern);
		}

		return returnDate;
	}

	/**
	 * Verifica se é uma data valida.
	 */
	public static boolean isValidDate(final String date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(VulpeDateUtil.DDMMYYYY,
				VulpeConstants.PORTUGUESE_LOCALE);
		if (date != null) {
			try {
				return date.equals(dateFormat.format(dateFormat.parse(date)));
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static boolean isWeekend(final Date date) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	public static Date overrideField(final Date date, final int field, final int value) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(field, value);
		return calendar.getTime();
	}

	/**
	 * Este método recebe um numero que representa uma quantidade de minutos e
	 * devolve uma string no formato hh:mm
	 */
	public static String getFormatedTime(final int minutes) {
		// Converte a duração em minutos para a duração em horas e minutos.
		final int hours = minutes / 60;
		final int min = minutes % 60;
		final StringBuilder hoursString = new StringBuilder();
		final StringBuilder minutesString = new StringBuilder();
		// Se a hora ou o minuto for menor que 10, colocar o zero a esquerda
		if (hours < 10) {
			hoursString.append("0").append(hours);
		}
		if (min < 10) {
			minutesString.append("0").append(minutes);
		}
		return hoursString.toString() + ":" + minutesString.toString();
	}

	/**
	 * Este método recebe um numero que representa uma quantidade de minutos e
	 * devolve uma string no formato hh:mm
	 */
	public static String getFormatedTime(final Integer minutes) {
		return minutes == null ? null : getFormatedTime(minutes.intValue());
	}

	/**
	 * Este método recebe uma string no formato hh:mm e retorna o total de
	 * minutos da duração passada.
	 */
	public static Integer getTimeInMinutes(final String duration) {
		Integer totalMinutes = null;
		if (!"".equals(duration)) {
			final int index = duration.indexOf(':');

			final Integer hours = Integer.valueOf(duration.substring(0, index));
			final Integer minutes = Integer.valueOf(duration.substring(index + 1));

			totalMinutes = Integer.valueOf((hours.intValue() * 60) + minutes.intValue());

		}
		return totalMinutes;
	}

	/**
	 * retorna a mesma data com os campos horas, minutos e segundos zerados.
	 */
	public static Date truncateTime(final Date date) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date.setTime(calendar.getTime().getTime());
		return date;
	}

	public static boolean isValidTime(final String time) {
		return isValidTime(time, false);
	}

	public static boolean isValidTime(final String time, final boolean lenient) {
		boolean valid = false;
		final SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(HHMM);
		if (lenient) {
			try {
				final int index = time.indexOf(':');
				// Integer hours = Integer.valueOf(time.substring(0, index));
				final Integer minutes = Integer.valueOf(time.substring(index + 1));
				if ((minutes != null) && (minutes.intValue() < 60)) {
					valid = true;
				}
			} catch (Exception e) {
				valid = false;
			}

		} else {
			sdf.setLenient(lenient);
			if (HHMM.length() != time.length()) {
				return valid;
			}
			try {
				sdf.parse(time);
				valid = true;
			} catch (ParseException e) {
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Verifica se a string representa uma data válida
	 */
	public static boolean isDateValid(final String date) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering in VulpeDateUtil.isDateValid(String date) - Start");
			LOG.debug("date: " + date);
		}
		boolean valid = false;

		try {
			final String newDate = date.trim();

			if (newDate.length() != DDMMYYYY.length()) {
				throw new ParseException("Tamanho inválido para um campo data.", newDate.length());
			}

			try {
				final StringTokenizer stringTokenizer = new StringTokenizer(newDate, "/");

				if (stringTokenizer.countTokens() != 3) {
					return false;
				}

			} catch (NumberFormatException n) {
				return false;
			}

			final SimpleDateFormat dateFormat = new SimpleDateFormat(DDMMYYYY,
					VulpeConstants.PORTUGUESE_LOCALE);
			dateFormat.setLenient(false);
			dateFormat.parse(newDate);
			valid = true;
		} catch (ParseException pe) {
			valid = false;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Leaving de VulpeDateUtil.isDateValid(String date) - End");
		}
		return valid;
	}

	/**
	 * Retrieves date on extensive format. Example: 01 of Jully of 2010.
	 *
	 * @return
	 */
	public String getExtensiveDate() {
		final SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
				DateFormat.MEDIUM, VulpeConstants.PORTUGUESE_LOCALE);
		format.applyPattern("MMMM");
		final Calendar calendar = Calendar.getInstance();
		final String month = format.format(calendar.getTime());
		final String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		final String year = Integer.toString(calendar.get(Calendar.YEAR));
		final String ofSepareate = " of ";
		return day + ofSepareate + month + ofSepareate + year;
	}

	/**
	 * Retorna a diferença em dias das duas datas informadas
	 */
	public static long getDaysDifference(final Date begin, final Date end) {

		long milliseconds;
		long days;

		final Calendar calendarBegin = new GregorianCalendar();
		final Calendar calendarEnd = new GregorianCalendar();

		calendarBegin.setTime(begin);
		calendarBegin.set(Calendar.HOUR_OF_DAY, 0);
		calendarBegin.set(Calendar.MINUTE, 0);
		calendarBegin.set(Calendar.SECOND, 0);
		calendarBegin.set(Calendar.MILLISECOND, 0);

		calendarEnd.setTime(end);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
		calendarEnd.set(Calendar.MINUTE, 0);
		calendarEnd.set(Calendar.SECOND, 0);
		calendarEnd.set(Calendar.MILLISECOND, 0);

		milliseconds = calendarEnd.getTime().getTime() - calendarBegin.getTime().getTime();

		days = milliseconds / 86400000;

		return days;
	}

	/**
	 * Converte uma string para data
	 */
	public static Date convertStringToDate(final String date) throws ParseException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering in VulpeDateUtil.convertStringToDate(String date) - Start");
			LOG.debug("date: " + date);
		}

		final SimpleDateFormat format = new SimpleDateFormat(DDMMYYYY,
				VulpeConstants.PORTUGUESE_LOCALE);
		format.setLenient(false);
		final Date formatedDate = format.parse(date);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Leaving of VulpeDateUtil.convertStringToDate(String date) - End");
		}

		return formatedDate;

	}

	/**
	 * Converte uma string representando uma hora para um objeto data
	 */
	public static Date convertStringToTime(final String time) throws ParseException {
		final SimpleDateFormat timeFormat = new SimpleDateFormat(HHMM,
				VulpeConstants.PORTUGUESE_LOCALE);
		timeFormat.setLenient(false);
		return timeFormat.parse(time);
	}

	/**
	 * Converte uma string com data e hora para um objeto data
	 */
	public static Date convertStringToDateTime(final String dateTime) throws ParseException {
		final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DDMMYYYY + " " + HHMM,
				VulpeConstants.PORTUGUESE_LOCALE);
		dateTimeFormat.setLenient(false);
		return dateTimeFormat.parse(dateTime);
	}

	public static String convertDateTimeToString(final Date dateTime) {
		String formatedDateTime = null;
		if (!VulpeValidationUtil.isEmpty(formatedDateTime)) {
			final SimpleDateFormat formatDateTime = new SimpleDateFormat(DDMMYYYY + " " + HHMM,
					VulpeConstants.PORTUGUESE_LOCALE);
			formatedDateTime = formatDateTime.format(formatedDateTime);
		}
		return formatedDateTime;
	}

	public static String convertDateToString(final Date dateTime) {
		String formatedDateTime = null;
		if (!VulpeValidationUtil.isEmpty(dateTime)) {
			final SimpleDateFormat formatDateTime = new SimpleDateFormat(DDMMYYYY,
					VulpeConstants.PORTUGUESE_LOCALE);
			formatedDateTime = formatDateTime.format(dateTime);
		}
		return formatedDateTime;
	}

	public static String convertDateTimeSecondToString(final Date dateTime) {
		String formatedDateTime = null;
		if (!VulpeValidationUtil.isEmpty(dateTime)) {
			final SimpleDateFormat formatDateTime = new SimpleDateFormat(DDMMYYYY + " " + HHMMSS,
					VulpeConstants.PORTUGUESE_LOCALE);
			formatedDateTime = formatDateTime.format(dateTime);
		}
		return formatedDateTime;
	}

	/**
	 * Converte hora para string
	 */
	public static String convertTimeToString(final Date time) {
		String formatedTime = "";
		if (time != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(HHMM,
					VulpeConstants.PORTUGUESE_LOCALE);
			formatedTime = dateFormat.format(time);
		}
		return formatedTime;
	}

	/**
	 * Converte um objeto date em uma String com o dia da semana que ele
	 * representa abreviado (Seg/Ter/Qua/Qui/Sex).
	 *
	 * Ex: Data: 04/10/2005 Valor retornado: Ter
	 */
	public static String getExtensiveAbbreviatedDate(final Date date) {
		final SimpleDateFormat formatDate = new SimpleDateFormat(VulpeConstants.SIMPLE_DATE_FORMAT,
				VulpeConstants.PORTUGUESE_LOCALE);
		return formatDate.format(date);
	}

	/**
	 * Converte uma data para a mesma data contendo o ultimo minuto do dia. Ex:
	 * Entrada: 01/01/2005 Retorno: 01/01/2005 23:59:59
	 */
	public static Date createFinalDateQuery(final Date date) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * Converte uma data para a mesma data contendo o primeiro minuto do dia.
	 * Ex: Entrada: 01/01/2005 Retorno: 01/01/2005 00:00:00
	 */
	public static Date createInitialDateQuery(final Date date) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Retorna a diferença em milisegundos entre as duas datas
	 */
	public static long getMillisecondDifference(final Date begin, final Date end) {
		long milliseconds;

		final Calendar dtBegin = new GregorianCalendar();
		final Calendar dtEnd = new GregorianCalendar();

		dtBegin.setTime(begin);
		dtBegin.set(Calendar.HOUR_OF_DAY, dtBegin.get(Calendar.HOUR_OF_DAY));
		dtBegin.set(Calendar.MINUTE, dtBegin.get(Calendar.MINUTE));
		dtBegin.set(Calendar.SECOND, dtBegin.get(Calendar.SECOND));
		dtBegin.set(Calendar.MILLISECOND, dtBegin.get(Calendar.MILLISECOND));

		dtEnd.setTime(end);
		dtEnd.set(Calendar.HOUR_OF_DAY, dtEnd.get(Calendar.HOUR_OF_DAY));
		dtEnd.set(Calendar.MINUTE, dtEnd.get(Calendar.MINUTE));
		dtEnd.set(Calendar.SECOND, dtEnd.get(Calendar.SECOND));
		dtEnd.set(Calendar.MILLISECOND, dtEnd.get(Calendar.MILLISECOND));

		milliseconds = dtEnd.getTime().getTime() - dtBegin.getTime().getTime();
		return milliseconds;
	}

	/**
	 * Retorna a diferenca, em minutos, entre as datas
	 */
	public static int getMinutesDifference(final Date begin, final Date end) {
		return (int) getMillisecondDifference(begin, end) / 60000;
	}

	/**
	 * Determina a quantidade de minutos de uma atividade que ocorreram dentro
	 * do período especificado. O período é delimitado pelos campos de hora e
	 * minuto dos parâmetros "inicio" e "fim" - os campos de ano, mês e dia
	 * destes dois parâmetros não são levados em consideração.
	 */
	private static int calculateTruncatedTime(final Date dateTimeBegin, final Date dateTimeEnd,
			final Date begin, final Date end) {

		if (begin.compareTo(end) > 0) {
			final Calendar calendarBegin1 = new GregorianCalendar();
			calendarBegin1.setTime(begin);
			calendarBegin1.set(Calendar.HOUR_OF_DAY, 0);
			calendarBegin1.set(Calendar.MINUTE, 0);
			calendarBegin1.set(Calendar.SECOND, 0);

			final Calendar calendarEnd2 = new GregorianCalendar();
			calendarEnd2.setTime(calendarBegin1.getTime());
			calendarEnd2.add(Calendar.DAY_OF_YEAR, 1);

			return calculateTruncatedTime(dateTimeBegin, dateTimeEnd, calendarBegin1.getTime(), end)
					+ calculateTruncatedTime(dateTimeBegin, dateTimeEnd, begin, calendarEnd2
							.getTime());
		}

		int returnedTime = 0;
		final Calendar periodBegin = new GregorianCalendar();
		periodBegin.setTime(begin);
		final Calendar periodEnd = new GregorianCalendar();
		periodEnd.setTime(end);
		final Calendar startActivity = new GregorianCalendar();
		startActivity.setTime(dateTimeBegin);

		/**
		 * Nos parâmetros "inicio" e "fim", o dia deve ser atualizado para ser o
		 * mesmo dia do inicio da atividade, para se calcular as diferenças
		 */
		periodBegin.set(startActivity.get(Calendar.YEAR), startActivity.get(Calendar.MONTH),
				startActivity.get(Calendar.DATE));
		periodEnd.set(startActivity.get(Calendar.YEAR), startActivity.get(Calendar.MONTH),
				startActivity.get(Calendar.DATE));

		/** aplicando uma correção para periodo noturno */
		if (periodBegin.getTime().compareTo(periodEnd.getTime()) > 0) {
			periodEnd.add(Calendar.DATE, 1);
		}
		final Date start = periodBegin.getTime();
		final Date finish = periodEnd.getTime();

		final int period = getMinutesDifference(start, finish);
		final int activityTime = getMinutesDifference(dateTimeBegin, dateTimeEnd);

		if (dateTimeBegin.compareTo(start) < 0) {
			returnedTime = Math.max(Math.min(getMinutesDifference(start, dateTimeEnd), period), 0); // a,
			// b,
			// c
		} else if (dateTimeBegin.compareTo(finish) > 0) {
			periodBegin.add(Calendar.DATE, 1);
			returnedTime = Math.max(Math.min(getMinutesDifference(periodBegin.getTime(),
					dateTimeEnd), period), 0); // d,
			// e
		} else if (dateTimeEnd.compareTo(finish) < 0) {
			returnedTime = activityTime; // f
		} else {
			periodBegin.add(Calendar.DATE, 1);
			if (dateTimeEnd.compareTo(periodBegin.getTime()) > 0) {
				returnedTime = activityTime - period; // g
			} else {
				returnedTime = getMinutesDifference(dateTimeBegin, finish); // h
			}
		}
		return returnedTime;
	}

	/**
	 * Verifica um periodo de horas.
	 *
	 * Verifica se a hora informada está dentro do periodo inicial e final
	 * informado ( desconsiderando o dia ).
	 */
	public static boolean checkTimePeriod(final Date beginHour, final Date endHour, final Date hour) {

		final Calendar calendarBegin = Calendar.getInstance();
		calendarBegin.setTime(beginHour);
		final Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endHour);
		final Calendar calendarTime = Calendar.getInstance();
		calendarTime.setTime(hour);

		boolean betweenPeriod = false;

		calendarBegin.set(Calendar.DAY_OF_MONTH, 1);
		calendarBegin.set(Calendar.MONTH, 1);
		calendarBegin.set(Calendar.YEAR, 1970);

		calendarEnd.set(Calendar.DAY_OF_MONTH, 2);
		calendarEnd.set(Calendar.MONTH, 1);
		calendarEnd.set(Calendar.YEAR, 1970);

		calendarTime.set(Calendar.DAY_OF_MONTH, 1);
		calendarTime.set(Calendar.MONTH, 1);
		calendarTime.set(Calendar.YEAR, 1970);

		// Verifica se esta dentro do periodo (no dia 1)
		if (calendarTime.after(calendarBegin) && calendarTime.before(calendarEnd)) {
			betweenPeriod = true;
		} else {
			// Se nao estiver, verifica se esta dentro do periodo no dia 2.
			calendarTime.set(Calendar.DAY_OF_MONTH, 2);
			if (calendarTime.after(calendarBegin) && calendarTime.before(calendarEnd)) {
				betweenPeriod = true;
			}
		}
		return betweenPeriod;
	}

	/**
	 * Retrieves time of activity on period.
	 *
	 * @param dateTimeStart
	 * @param dateTimeEnd
	 * @param start
	 * @param end
	 * @return
	 */
	public static int calculateTruncatedTimeByDays(final Date dateTimeStart,
			final Date dateTimeEnd, final Date start, final Date end) {

		int minutes = 0;

		final Date startActivity = new Date(dateTimeStart.getTime());
		final Date endActivity = new Date(dateTimeEnd.getTime());

		final Date periodBegin = new Date(start.getTime());
		final Date periodEnd = new Date(end.getTime());

		if (startActivity.compareTo(endActivity) < 0) {
			// normaliza as data para o período (início e fim). Deixam ambos no
			// mesmo dia, mês e ano.
			// O que importa são os horários
			final Calendar calendarBegin = new GregorianCalendar();
			calendarBegin.setTime(periodBegin);
			final Date datePeriodStart = calendarBegin.getTime();

			final Calendar calendarEnd = new GregorianCalendar();
			calendarEnd.setTime(periodEnd);
			calendarEnd.set(Calendar.DAY_OF_MONTH, calendarBegin.get(Calendar.DAY_OF_MONTH));
			calendarEnd.set(Calendar.MONTH, calendarBegin.get(Calendar.MONTH));
			calendarEnd.set(Calendar.YEAR, calendarBegin.get(Calendar.YEAR));
			final Date datePeriodEnd = calendarEnd.getTime();

			Date dtBeginActivity = startActivity;
			Date dtEndActivity = endActivity;
			final Calendar dayEndStart = new GregorianCalendar();
			final Calendar beginNextDay = new GregorianCalendar();
			do {
				// calcula o fim para o mesmo dia de início
				dayEndStart.setTime(dtBeginActivity);
				dayEndStart.set(Calendar.HOUR_OF_DAY, 23);
				dayEndStart.set(Calendar.MINUTE, 59);
				dayEndStart.set(Calendar.SECOND, 59);
				dayEndStart.add(Calendar.SECOND, 1);
				final Date dTimeEnd = dayEndStart.getTime();
				// se fim real for maior que fim do dia de início, utiliza fim
				// do dia de início,
				// do contrário é último dia da atividade e deve ser o fim real
				if (endActivity.compareTo(dTimeEnd) > 0) {
					dtEndActivity = dTimeEnd;
				} else {
					dtEndActivity = endActivity;
				}
				final int parcialMinutes = calculateTruncatedTime(dtBeginActivity, dtEndActivity,
						datePeriodStart, datePeriodEnd);
				minutes += parcialMinutes;
				// atualiza o próximo início
				beginNextDay.setTime(dtEndActivity);
				dtBeginActivity = beginNextDay.getTime();
			} while (dtEndActivity.compareTo(endActivity) != 0);
		}
		return minutes;
	}

	/**
	 * Adds days on Date.
	 *
	 * @param date
	 * @param numberOfDays
	 * @return
	 */
	public static Date addDaysOnDate(final Date date, final int numberOfDays) {
		Date addedDate = null;
		if (date != null) {
			final Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, numberOfDays);
			addedDate = calendar.getTime();
		}
		return addedDate;
	}

	/**
	 * Retrieves Time Zone (GTM)
	 *
	 * @return
	 */
	public static String getOffSetTimeZone() {
		final int off = Calendar.getInstance().get(Calendar.ZONE_OFFSET);
		final StringBuilder offset = new StringBuilder(VulpeConstants.STRING_NULL);
		if (off < 0) {
			offset.append("-");
		} else {
			offset.append("+");
		}
		offset.append(getFormatedTime(Math.abs(off) * 60 / 3600000));
		return offset.toString();
	}

	/**
	 * Calcula a diferença entre os índices dos dias ao longo de um ano entre
	 * duas datas (dataMenor deve ser menor do que dataMaior)
	 */
	public static int getDifferencesBetweenIndicateDays(final Date minorDate, final Date majorDate) {
		int numDiff = 0;

		if (majorDate.after(minorDate)) {
			final Calendar calendarMinor = new GregorianCalendar();
			calendarMinor.setTime(minorDate);
			final int dayYearMinor = calendarMinor.get(Calendar.DAY_OF_YEAR);
			final int yearMinor = calendarMinor.get(Calendar.YEAR);

			final Calendar calendarMajor = new GregorianCalendar();
			calendarMajor.setTime(majorDate);
			int dayYearMaior = calendarMajor.get(Calendar.DAY_OF_YEAR);
			final int yearMaior = calendarMajor.get(Calendar.YEAR);

			if (yearMinor < yearMaior) {
				int auxYear = yearMinor;
				final Calendar calendarAux = calendarMinor;
				while (auxYear < yearMaior) {
					dayYearMaior += calendarAux.getActualMaximum(Calendar.DAY_OF_YEAR);
					auxYear++;
					calendarAux.set(Calendar.YEAR, auxYear);
				}
			}

			numDiff = (dayYearMaior - dayYearMinor);
		}

		return numDiff;
	}

	/**
	 * Checks whether the dates are on the same day.
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDatesInSameDay(final Date date1, final Date date2) {
		if (truncateTime(date1).equals(truncateTime(date2))) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if data is between period.
	 *
	 * @param date
	 * @param periodStart
	 * @param periodEnd
	 * @return
	 */
	public static boolean validatePeriodBetweenDate(final Date date, final Date periodStart,
			final Date periodEnd) {
		return (date.after(periodStart) && (periodEnd == null || date.before(periodEnd)));
	}

	/**
	 * Retrieves dates by days of week on month
	 *
	 * @param days
	 * @return
	 */
	public static List<Date> getDatesOnMonth(final DaysOfWeek... daysOfWeek) {
		final List<Date> dates = new ArrayList<Date>();
		if (daysOfWeek != null) {
			final Calendar calendar = Calendar.getInstance();
			final int firstDay = Calendar.getInstance().getActualMinimum(Calendar.DATE);
			final int lastDay = Calendar.getInstance().getActualMaximum(Calendar.DATE);
			final int period = lastDay - firstDay;
			for (int i = 1; i <= period; i++) {
				calendar.set(Calendar.DATE, i);
				final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				for (DaysOfWeek day : daysOfWeek) {
					if (dayOfWeek == day.getValue()) {
						dates.add(calendar.getTime());
						continue;
					}
				}
			}
		}
		return dates;
	}

	public enum DaysOfWeek {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

		public int getValue() {
			int dayOfWeek = 0;
			switch (this) {
			case SUNDAY:
				dayOfWeek = 1;
				break;
			case MONDAY:
				dayOfWeek = 2;
				break;
			case TUESDAY:
				dayOfWeek = 3;
				break;
			case WEDNESDAY:
				dayOfWeek = 4;
				break;
			case THURSDAY:
				dayOfWeek = 5;
				break;
			case FRIDAY:
				dayOfWeek = 6;
				break;
			case SATURDAY:
				dayOfWeek = 7;
				break;
			default:
				dayOfWeek = 0;
				break;
			}
			return dayOfWeek;
		}
	}

	/**
	 * Retrieves the first date of the month.
	 *
	 * @return
	 */
	public static Date getFirstDateOfTheMonth() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Retrieves the last date of the month.
	 *
	 * @return
	 */
	public static Date getLastDateOfTheMonth() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}