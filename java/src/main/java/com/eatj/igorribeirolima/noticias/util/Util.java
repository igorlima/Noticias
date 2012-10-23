package com.eatj.igorribeirolima.noticias.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.eatj.igorribeirolima.noticias.model.domain.type.Regex;

public class Util {
	
	public static Date converterStringToDate(String strDate) {

		if (isDate(strDate)) {
			DateFormat formatter;

			if (Pattern.matches(Regex.DATE_HORA.get(), strDate))
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			else
				formatter = new SimpleDateFormat("dd/MM/yyyy");

			try {
				return (Date) formatter.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}
	
	public static boolean isDate(String strDate) {

		if (Pattern.matches(Regex.DATE.get(), strDate))
			return true;
		else
			return false;

	}
	
	public static String converterDateToString(Date dt) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dt);
	}
	
}
