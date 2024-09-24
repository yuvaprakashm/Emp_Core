package net.texala.employee.input.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.model.Employee;
import net.texala.employee.service.impl.EmployeeServiceImpl;

public class InputValidator {
	private EmployeeServiceImpl employeeServiceImpl;

	public InputValidator(EmployeeServiceImpl employeeServiceImpl) {
		this.employeeServiceImpl = employeeServiceImpl;
	}

	public Long promptForLong(Scanner scanner, String message) {
		Long value = null;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(message);
			try {
				value = scanner.nextLong();
				scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid input. Please enter a valid number.");
				scanner.nextLine();
			}
		}
		return value;
	}

	public double promptForValidSalary(Scanner scanner) {
		double salary = -1;
		boolean validInput = false;
		while (!validInput) {
			try {
				salary = promptForDouble(scanner, "Enter Salary: ");
				if (salary < 0) {
					System.out.println("Error: Salary must be positive.");
				} else {
					validInput = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid input. Please enter a valid salary.");
				scanner.nextLine();
			}
		}
		return salary;
	}

	public String promptForUniqueEmail(Scanner scanner, Long id) {
		String email;
		do {
			email = promptForEmail(scanner, "Enter New Email: ");
			if (!isValidEmailFormat(email)) {
				System.out.println("Error: Invalid email format. Please enter a valid email.");
			} else if (!isEmailUnique(email, id)) {
				System.out.println("Error: Email ID '" + email + "' is already in use.");
			}
		} while (!isValidEmailFormat(email) || !isEmailUnique(email, id));
		return email;
	}

	public boolean isEmailUnique(String email, Long id) {
		Employee employee = employeeServiceImpl.findByEmail(email);
		return employee == null || employee.getId().equals(id);
	}

	public boolean isValidEmailFormat(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return email != null && email.matches(emailRegex);
	}

	private String promptForEmail(Scanner scanner, String message) {
		System.out.print(message);
		return scanner.nextLine();
	}

	public String promptForString(Scanner scanner, String message) {
		String input;
		while (true) {
			System.out.print(message);
			input = scanner.nextLine();
			if (input.matches("[a-zA-Z]+")) {
				break;
			} else {
				System.out.println("Invalid input. Please enter only alphabets.");
			}
		}
		return input;
	}

	public static double promptForDouble(Scanner scanner, String message) {
		double value = -1;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(message);
			try {
				value = scanner.nextDouble();
				scanner.nextLine();
				validInput = true;
			} catch (ServiceException e) {
				System.out.println("Error: Invalid input. Please enter a valid number.");
				scanner.nextLine();
			}
		}
		return value;
	}

	public LocalDate promptForDate(Scanner scanner, String message) {
		LocalDate date = null;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(message);
			String input = scanner.nextLine();
			try {
				date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				validInput = true;
			} catch (DateTimeParseException e) {
				System.out.println("Error: Invalid date format. Please enter the date in 'dd-MM-yyyy' format.");
			}
		}
		return date;
	}

	public Employee collectEmployeeData(Scanner scanner, Long id) {
		if (employeeServiceImpl.findAll().stream().anyMatch(emp -> emp.getId().equals(id))) {
			System.out.println("Error: Employee with ID '" + id + "' already exists.");
			return null;
		}
		String name = promptForString(scanner, "Enter Name: ");
		String email = promptForUniqueEmail(scanner, id);
		double salary = promptForValidSalary(scanner);
		LocalDate dob = promptForDate(scanner, "Enter DOB (dd-MM-yyyy): ");
		return new Employee(id, name, email, salary, dob);
	}

	public void display() {
		System.out.println("1. Add ");
		System.out.println("2. Update ");
		System.out.println("3. Delete ");
		System.out.println("4. FindbyID");
		System.out.println("5. FetchAll ");
		System.out.println("6. Exit");
	}

	public Employee collectEmployeeData(Scanner scanner, Long id, boolean isUpdate) {
	    if (isUpdate && employeeServiceImpl.findAll().stream().noneMatch(emp -> emp.getId().equals(id))) {
	        System.out.println("Error: Employee with ID '" + id + "' not found.");
	        return null;  
	    }

	    String name = promptForString(scanner, "Enter Name: ");
	    String email = promptForUniqueEmail(scanner, id);
	    double salary = promptForValidSalary(scanner);
	    LocalDate dob = promptForDate(scanner, "Enter DOB (dd-MM-yyyy): ");
	    
	    return new Employee(id, name, email, salary, dob);
	}

}