package net.texala.employee.web.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.service.impl.EmployeeServiceImpl;

public class EmployeeController {

	private static EmployeeService employeeService = new EmployeeServiceImpl();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int option = 0;

		while (option != 6) {
			System.out.println("\nChoose Operation :");
			System.out.println("1. Add");
			System.out.println("2. Update");
			System.out.println("3. Delete");
			System.out.println("4. FindbyID");
			System.out.println("5. Display all");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");

			try {
				option = scanner.nextInt();
				scanner.nextLine();
				switch (option) {
				case 1:
					add(scanner);
					break;
				case 2:
					update(scanner);
					break;
				case 3:
					delete(scanner);
					break;
				case 4:
					findById(scanner);
					break;
				case 5:
					findAll();
					break;
				case 6:
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Invalid choice! Try again.");
				}
			} catch (ServiceException e) {
				System.out.println("Error: Invalid input. Please enter correct value.");
				scanner.nextLine();
			}
		}
		scanner.close();
	}

	private static void add(Scanner scanner) {
		Long id = promptForLong(scanner, "Enter Employee ID: ");
		if (employeeService.findById(id) != null) {
			System.out.println("Error: Employee with ID '" + id + "' already exists.");
			return;
		}
		String name = promptForString(scanner, "Enter Name: ");
		String email = promptForUniqueEmail(scanner, id);
		double salary = promptForValidSalary(scanner);
		LocalDate dob = promptForDate(scanner, "Enter DOB (dd-MM-yyyy): ");
		Employee employee = new Employee(id, name, email, salary, dob);
		employeeService.add(employee);
		System.out.println("Employee added successfully.");
	}

	private static void update(Scanner scanner) {
		Long id = promptForLong(scanner, "Enter Employee ID to update: ");
		try {
			Employee existingEmployee = employeeService.findById(id);
			if (existingEmployee == null) {
				System.out.println("Error: Employee with ID '" + id + "' not found.");
				return;
			}
			String name = promptForString(scanner, "Enter Name: ");
			String email = promptForUniqueEmail(scanner, id);
			double salary = promptForValidSalary(scanner);
			LocalDate dob = promptForDate(scanner, "Enter DOB (dd-MM-yyyy): ");
			Employee updatedEmployee = new Employee(id, name, email, salary, dob);
			employeeService.update(updatedEmployee);
			System.out.println("Employee updated successfully.");

		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void delete(Scanner scanner) {
		System.out.print("Enter Employee ID to delete: ");
		Long id = scanner.nextLong();
		scanner.nextLine();
		try {
			employeeService.delete(id);
			System.out.println("Employee deleted successfully.");
		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void findById(Scanner scanner) {
		System.out.print("Enter Employee ID to find: ");
		Long id = scanner.nextLong();
		Employee employee = employeeService.findById(id);
		if (employee != null) {
			System.out.println("Employee Details: " + employee);
		} else {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
		}
	}

	private static void findAll() {
		List<Employee> employees = employeeService.findAll();
		employees.forEach(System.out::println);
	}

	private static Long promptForLong(Scanner scanner, String message) {
		Long value = null;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(message);
			try {
				value = scanner.nextLong();
				scanner.nextLine();
				validInput = true;
			} catch (ServiceException e) {
				System.out.println("Error: Invalid input. Please enter a valid number.");
				scanner.nextLine();
			}
		}

		return value;
	}

	private static double promptForValidSalary(Scanner scanner) {
		double salary = -1;
		do {
			try {
				salary = promptForDouble(scanner, "Enter Salary: ");
				if (salary < 0) {
					System.out.println("Error: Salary must be positive.");
				}
			} catch (ServiceException e) {
				System.out.println("Error: Invalid input. Please enter a valid salary.");
				scanner.nextLine();
			}
		} while (salary < 0);
		return salary;
	}

	private static String promptForUniqueEmail(Scanner scanner, Long id) {
		String email;
		do {
			email = promptForEmail(scanner, "Enter New Email: ");
			if (!isValidEmailFormat(email)) {
				System.out.println("Error: Invalid email format. Please enter a valid email.");
			} else if (!isEmailUnique(email, id)) {
				System.out
						.println("Error: Email ID '" + email + "' is already in use. Please enter a different email.");
			}
		} while (!isValidEmailFormat(email) || !isEmailUnique(email, id));
		return email;
	}

	private static boolean isEmailUnique(String email, Long id) {
		Employee employee = employeeService.findByEmail(email);
		return employee == null || employee.getId().equals(id);
	}

	private static boolean isValidEmailFormat(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return email != null && email.matches(emailRegex);
	}

	private static String promptForEmail(Scanner scanner, String message) {
		String input;
		System.out.print(message);
		input = scanner.nextLine();
		return input;
	}

	private static String promptForString(Scanner scanner, String message) {
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

	private static double promptForDouble(Scanner scanner, String message) {
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

	private static LocalDate promptForDate(Scanner scanner, String message) {
		LocalDate date = null;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(message);
			String input = scanner.nextLine();
			try {
				date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				validInput = true;
			} catch (ServiceException e) {
				System.out.println("Error: Invalid date format. Please enter the date in the format dd-MM-yyyy.");
			}
		}
		return date;
	}

}
