package net.texala.employee.input.util;

import static net.texala.employee.constant.Constants.DATE_FORMAT;
import static net.texala.employee.constant.Constants.EMAIL_REGEX;
import static net.texala.employee.constant.Constants.ERROR_DATE_INVALID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.UUID;

public class Utility {

	public static String generateId() {
		return UUID.randomUUID().toString();
	}

	public static boolean hasId(String id) {
		try {
			Long.parseLong(id);
			return true;
		} catch (InputMismatchException e) {
			return false;
		}
	}

	public static boolean hasDouble(String id) {
		try {
			Double.parseDouble(id);
			return true;
		} catch (InputMismatchException e) {
			return false;
		}
	}

	public static boolean hasEmail(String email) {
		return email != null && email.matches(EMAIL_REGEX);
	}

	public static LocalDate toLocalDate(String date) {
		try {
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
		} catch (DateTimeParseException e) {
			throw new RuntimeException(ERROR_DATE_INVALID);
		}
	}

}