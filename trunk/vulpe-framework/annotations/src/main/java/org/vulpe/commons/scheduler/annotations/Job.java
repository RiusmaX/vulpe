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
package org.vulpe.commons.scheduler.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells Vulpe to schedule job class.<br>
 * <br>
 * 
 * <h1>CronTrigger Tutorial</h1>
 * 
 * <h2><a name="CronTriggersTutorial-Introduction"></a>Introduction</h2>
 * 
 * <p>
 * <tt>cron</tt> is a UNIX tool that has been around for a long time, so its
 * scheduling capabilities are powerful and proven. The <tt>CronTrigger</tt>
 * class is based on the scheduling capabilities of cron.
 * </p>
 * 
 * <p>
 * <tt>CronTrigger</tt> uses "cron expressions", which are able to create firing
 * schedules such as: "At 8:00am every Monday through Friday" or "At 1:30am
 * every last Friday of the month".
 * </p>
 * 
 * <p>
 * Cron expressions are powerful, but can be pretty confusing. This tutorial
 * aims to take some of the mystery out of creating a cron expression, giving
 * users a resource which they can visit before having to ask in a forum or
 * mailing list.
 * </p>
 * 
 * <h2><a name="CronTriggersTutorial-Format"></a>Format</h2>
 * 
 * <p>
 * A cron expression is a string comprised of 6 or 7 fields separated by white
 * space. Fields can contain any of the allowed values, along with various
 * combinations of the allowed special characters for that field. The fields are
 * as follows:
 * </p>
 * <table cellpadding="3" cellspacing="1">
 * <tbody>
 * <tr>
 * <th>Field Name</th>
 * <th>Mandatory</th>
 * <th>Allowed Values</th>
 * <th>Allowed Special Characters</th>
 * </tr>
 * <tr>
 * <td>Seconds</td>
 * <td>YES</td>
 * 
 * <td>0-59</td>
 * <td>, - * /</td>
 * </tr>
 * <tr>
 * <td>Minutes</td>
 * <td>YES</td>
 * <td>0-59</td>
 * 
 * <td>, - * /</td>
 * </tr>
 * <tr>
 * <td>Hours</td>
 * <td>YES</td>
 * <td>0-23</td>
 * <td>, - * /</td>
 * 
 * </tr>
 * <tr>
 * <td>Day of month</td>
 * <td>YES</td>
 * <td>1-31</td>
 * <td>, - * ? / L W<br clear="all" />
 * </td>
 * </tr>
 * <tr>
 * 
 * <td>Month</td>
 * <td>YES</td>
 * <td>1-12 or JAN-DEC</td>
 * <td>, - * /</td>
 * </tr>
 * <tr>
 * <td>Day of week</td>
 * 
 * <td>YES</td>
 * <td>1-7 or SUN-SAT</td>
 * <td>, - * ? / L #</td>
 * </tr>
 * <tr>
 * <td>Year</td>
 * <td>NO</td>
 * 
 * <td>empty, 1970-2099</td>
 * <td>, - * /</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * So cron expressions can be as simple as this: <tt>&#42; * * * ? *</tt><br />
 * or more complex, like this:
 * <tt>0/5 14,18,3-39,52 * ? JAN,MAR,SEP MON-FRI 2002-2010</tt>
 * </p>
 * 
 * <h2><a name="CronTriggersTutorial-Specialcharacters"></a>Special characters</h2>
 * 
 * <ul>
 * <li><tt><b>&#42;</b></tt> (<em>"all values"</em>) - used to select all values
 * within a field. For example, "*" in the minute field means
 * <em>"every minute"</em>.</li>
 * </ul>
 * 
 * 
 * <ul>
 * <li><tt><b>?</b></tt> (<em>"no specific value"</em>) - useful when you need
 * to specify something in one of the two fields in which the character is
 * allowed, but not the other. For example, if I want my trigger to fire on a
 * particular day of the month (say, the 10th), but don't care what day of the
 * week that happens to be, I would put "10" in the day-of-month field, and "?"
 * in the day-of-week field. See the examples below for clarification.</li>
 * 
 * </ul>
 * 
 * 
 * <ul>
 * <li><tt><b>&#45;</b></tt> &#45; used to specify ranges. For example, "10-12"
 * in the hour field means <em>"the
    hours 10, 11 and 12"</em>.</li>
 * </ul>
 * 
 * 
 * <ul>
 * <li><tt><b>,</b></tt> &#45; used to specify additional values. For example,
 * "MON,WED,FRI" in the day-of-week field means
 * <em>"the days Monday, Wednesday, and Friday"</em>.</li>
 * 
 * </ul>
 * 
 * 
 * <ul>
 * <li><tt><b>/</b></tt> &#45; used to specify increments. For example, "0/15"
 * in the seconds field means <em>"the
    seconds 0, 15, 30, and 45"</em>. And "5/15" in the seconds field means
 * <em>"the seconds 5, 20, 35, and 50"</em>. You can also specify '/' after the
 * '<b>' character - in this case '</b>' is equivalent to having '0' before the
 * '/'. '1/3' in the day-of-month field means
 * <em>"fire every 3 days starting on the first day of the month"</em>.</li>
 * 
 * </ul>
 * 
 * 
 * <ul>
 * <li><tt><b>L</b></tt> (<em>"last"</em>) - has different meaning in each of
 * the two fields in which it is allowed. For example, the value "L" in the
 * day-of-month field means <em>"the last day of the month"</em> &#45; day 31
 * for January, day 28 for February on non-leap years. If used in the
 * day-of-week field by itself, it simply means "7" or "SAT". But if used in the
 * day-of-week field after another value, it means <em>"the last xxx day of the
    month"</em> &#45; for example "6L" means <em>"the last friday of the month"</em>.
 * You can also specify an offset from the last day of the month, such as "L-3"
 * which would mean the third-to-last day of the calendar month. <i>When using
 * the 'L' option, it is important not to specify lists, or ranges of values, as
 * you'll get confusing/unexpected results.</i></li>
 * </ul>
 * 
 * 
 * <ul>
 * <li><tt><b>W</b></tt> (<em>"weekday"</em>) - used to specify the weekday
 * (Monday-Friday) nearest the given day. As an example, if you were to specify
 * "15W" as the value for the day-of-month field, the meaning is: <em>"the
    nearest weekday to the 15th of the month"</em>. So if the 15th is a Saturday, the trigger will fire on Friday the 14th.
 * If the 15th is a Sunday, the trigger will fire on Monday the 16th. If the
 * 15th is a Tuesday, then it will fire on Tuesday the 15th. However if you
 * specify "1W" as the value for day-of-month, and the 1st is a Saturday, the
 * trigger will fire on Monday the 3rd, as it will not 'jump' over the boundary
 * of a month's days. The 'W' character can only be specified when the
 * day-of-month is a single day, not a range or list of days. <div
 * class="tip"><img src="../../images/Gear_s.png" width="20" height="20"
 * align="absmiddle" alt="Info" border="0"> The 'L' and 'W' characters can also
 * be combined in the day-of-month field to yield 'LW', which translates to
 * <em>"last weekday of the month"</em>. </div></li>
 * 
 * <li><tt><b>&#35;</b></tt> &#45; used to specify "the nth" XXX day of the
 * month. For example, the value of "6#3" in the day-of-week field means
 * <em>"the third Friday of the month"</em> (day 6 = Friday and "#3" = the 3rd
 * one in the month). Other examples: "2#1" = the first Monday of the month and
 * "4#5" = the fifth Wednesday of the month. Note that if you specify "#5" and
 * there is not 5 of the given day-of-week in the month, then no firing will
 * occur that month. <div class="tip"><img src="../../images/Gear_s.png"
 * width="20" height="20" align="absmiddle" alt="Info" border="0"> The legal
 * characters and the names of months and days of the week are not case
 * sensitive. <tt>MON</tt> is the same as <tt>mon</tt>. </div></li>
 * </ul>
 * 
 * <h2><a name="CronTriggersTutorial-Examples"></a>Examples</h2>
 * 
 * <p>
 * Here are some full examples:
 * </p>
 * <table cellpadding="3" cellspacing="1">
 * <tbody>
 * <tr>
 * <th>Expression</th>
 * <th>Meaning</th>
 * </tr>
 * <tr>
 * <td><tt>0 0 12 * * ?</tt></td>
 * 
 * <td>Fire at 12pm (noon) every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 ? * *</tt></td>
 * <td>Fire at 10:15am every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 * * ?</tt></td>
 * 
 * <td>Fire at 10:15am every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 * * ? *</tt></td>
 * <td>Fire at 10:15am every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 * * ? 2005</tt></td>
 * 
 * <td>Fire at 10:15am every day during the year 2005</td>
 * </tr>
 * <tr>
 * <td><tt>0 * 14 * * ?</tt></td>
 * <td>Fire every minute starting at 2pm and ending at 2:59pm, every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 0/5 14 * * ?</tt></td>
 * 
 * <td>Fire every 5 minutes starting at 2pm and ending at 2:55pm, every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 0/5 14,18 * * ?</tt></td>
 * <td>Fire every 5 minutes starting at 2pm and ending at 2:55pm, AND fire every
 * 5 minutes starting at 6pm and ending at 6:55pm, every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 0-5 14 * * ?</tt></td>
 * 
 * <td>Fire every minute starting at 2pm and ending at 2:05pm, every day</td>
 * </tr>
 * <tr>
 * <td><tt>0 10,44 14 ? 3 WED</tt></td>
 * <td>Fire at 2:10pm and at 2:44pm every Wednesday in the month of March.</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 ? * MON-FRI</tt></td>
 * 
 * <td>Fire at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 15 * ?</tt></td>
 * <td>Fire at 10:15am on the 15th day of every month</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 L * ?</tt></td>
 * 
 * <td>Fire at 10:15am on the last day of every month</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 L-2 * ?</tt></td>
 * 
 * <td>Fire at 10:15am on the 2nd-to-last last day of every month</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 ? * 6L</tt></td>
 * <td>Fire at 10:15am on the last Friday of every month</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 ? * 6L</tt></td>
 * 
 * <td>Fire at 10:15am on the last Friday of every month</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 ? * 6L 2002-2005</tt></td>
 * <td>Fire at 10:15am on every last friday of every month during the years
 * 2002, 2003, 2004 and 2005</td>
 * </tr>
 * <tr>
 * <td><tt>0 15 10 ? * 6#3</tt></td>
 * 
 * <td>Fire at 10:15am on the third Friday of every month</td>
 * </tr>
 * <tr>
 * <td><tt>0 0 12 1/5 * ?</tt></td>
 * <td>Fire at 12pm (noon) every 5 days every month, starting on the first day
 * of the month.</td>
 * </tr>
 * <tr>
 * <td><tt>0 11 11 11 11 ?</tt></td>
 * 
 * <td>Fire every November 11th at 11:11am.</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Job {

	String trigger() default "";

	String group() default "appJobs";

	String value() default "0 0 0 * * ?";
}