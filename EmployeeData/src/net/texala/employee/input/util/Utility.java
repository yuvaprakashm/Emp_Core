package net.texala.employee.input.util;

import static net.texala.employee.constant.Constants.DATE_FORMAT;
import static net.texala.employee.constant.Constants.EMAIL_REGEX;
import static net.texala.employee.constant.Constants.ERROR_DATE_INVALID;
import static net.texala.employee.constant.Constants.DATE_OF_BIRTH_CANNOT_BE_NULL;
import static net.texala.employee.constant.Constants.EMPLOYEE_AGE_MUST_BE_BETWEEN_18_AND_60;
import static net.texala.employee.constant.Constants.SALARY_MUST_BE_GREATER_THAN_20000;
import static net.texala.employee.constant.Constants.DATE_OF_BIRTH_CANNOT_BE_IN_FUTURE;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
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

	public static double hasSalary(double salary) {
		if (salary > 20000) {
			return salary;
		}
		throw new IllegalArgumentException(SALARY_MUST_BE_GREATER_THAN_20000);
	}

	public static LocalDate hasDate(LocalDate dob) {

		if (dob.isAfter(LocalDate.now()))
			throw new RuntimeException(DATE_OF_BIRTH_CANNOT_BE_IN_FUTURE);
		int age = Period.between(dob, LocalDate.now()).getYears();
		if (age < 18 || age > 60)
			throw new RuntimeException(EMPLOYEE_AGE_MUST_BE_BETWEEN_18_AND_60);
		return dob;
	}

}
