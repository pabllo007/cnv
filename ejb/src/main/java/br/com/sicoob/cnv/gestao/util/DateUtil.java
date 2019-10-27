package br.com.sicoob.cnv.gestao.util;

import static java.util.Calendar.SUNDAY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe utilitaria para trabalhar com data.
 *
 * @author Luis.Fernandez
 */
public class DateUtil {

	/** The Constant PATTERN. */
	private static final String PATTERN = "yyyy-MM-dd";

	/**
	 * Parses the.
	 *
	 * @param data the data
	 * @return the date
	 */
	public static Date parse(String data) {
		try {
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
			return simpleDateFormat.parse(data);
		} catch (final ParseException e) {
			return null;
		}
	}

	/**
	 * Plus.
	 *
	 * @param data the data
	 * @param dias the dias
	 * @return the date
	 */
	public static Date plus(Date data, Integer dias) {
		if (dias != null) {
			final Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.DATE, dias);
			return c.getTime();
		} else {
			return data;
		}
	}

	/**
	 * Parses the date.
	 *
	 * @param data    the data
	 * @param formato the formato
	 * @return the string
	 */
	public static String parseDate(Date data, String formato) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		return simpleDateFormat.format(data);
	}

	/**
	 * Checks if is domingo.
	 *
	 * @param data the data
	 * @return true, if is domingo
	 */
	public static boolean isDomingo(Date data) {
		final GregorianCalendar gc = getDateCalendar(data);
		final int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
		return diaSemana == SUNDAY;
	}

	/**
	 * Gets the date calendar.
	 *
	 * @param date the date
	 * @return the date calendar
	 */
	public static GregorianCalendar getDateCalendar(Date date) {
		final GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Minus.
	 *
	 * @param data the data
	 * @param dias the dias
	 * @return the date
	 */
	public static Date minus(Date data, Integer dias) {
		if (dias != null) {
			final Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.DATE, -dias);
			return c.getTime();
		} else {
			return data;
		}
	}

	/**
	 * Zerar horas.
	 *
	 * @param data the data
	 * @return the date
	 */
	public static Date zerarHoras(Date data) {
		try {
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.parse(format.format(data));
		} catch (final ParseException e) {
			return null;
		}
	}

	/**
	 * Checks if is hora.
	 *
	 * @param hora the hora
	 * @return true, if is hora
	 */
	public static boolean isMenorIgualHora(String hora) {
		boolean verificador = false;
		final Calendar now = new GregorianCalendar();
		final int nowHour = now.get(Calendar.HOUR);
		final int nowMin = now.get(Calendar.MINUTE);
		final String[] parts = hora.split(":");
		final int horaHour = Integer.valueOf(parts[0]);
		final int horaMinute = Integer.valueOf(parts[1]);
		if(60 * nowHour + nowMin <= 60 * horaHour + horaMinute) {
			verificador = true;
		}
		return verificador;
	}


}
