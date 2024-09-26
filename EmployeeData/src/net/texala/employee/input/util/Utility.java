package net.texala.employee.input.util;

import static net.texala.employee.constant.Constants.DATE_FORMAT;
import static net.texala.employee.constant.Constants.EMAIL_REGEX;
import static net.texala.employee.constant.Constants.ERROR_DATE_INVALID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class Utility {
	
	public static boolean hasId(String id) {
		try {
			Long.parseLong(id);
			return true;
		}catch (InputMismatchException e) {
			return false;
		}
	}
	
	public static boolean hasDouble(String id) {
		try {
			Double.parseDouble(id);
			return true;
		}catch (InputMismatchException e) {
			return false;
		}
	}
	
	public static boolean hasEmail(String email) {
		return email != null && email.matches(EMAIL_REGEX);
	}

	public static boolean hasDate(String date) {
			try {
				LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
				return true;
			} catch (DateTimeParseException e) {
				System.err.println(ERROR_DATE_INVALID);
		}
		return false;
	}


}