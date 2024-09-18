package net.texala.employee.web.rest;

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
		int option;

		do {
			System.out.println("\nChoose Operation :");
			System.out.println("1. Add Employee");
			System.out.println("2. Update Employee");
			System.out.println("3. Delete Employee");
			System.out.println("4. Find Employee by ID");
			System.out.println("5. Display all Employees");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			option = scanner.nextInt();

			switch (option) {
			case 1:
				addEmployee(scanner);
				break;
			case 2:
				updateEmployee(scanner);
				break;
			case 3:
				deleteEmployee(scanner);
				break;
			case 4:
				findEmployeeById(scanner);
				break;
			case 5:
				listAllEmployees();
				break;
			case 0:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice! Try again.");
			}
		} while (option != 0);

		scanner.close();
	}

	private static void addEmployee(Scanner scanner) {
		System.out.print("Enter Employee ID: ");
		Long id = scanner.nextLong();
		scanner.nextLine();

		try {
			if (employeeService.findId(id) != null) {
				System.out.println("Error: Employee with ID '" + id + "' already exists.");
				return;
			}

			System.out.print("Enter Name: ");
			String name = scanner.nextLine();

			System.out.print("Enter Email: ");
			String email = scanner.nextLine();
			if (!isEmailUnique(email, null)) {
				System.out.println("Error: Email ID '" + email + "' is already in use.");
				return;
			}

			System.out.print("Enter Salary: ");
			double salary = scanner.nextDouble();

			System.out.print("Enter DOB (dd-mm-yyyy): ");
			String dob = scanner.next();

			Employee employee = new Employee(id, name, email, salary, dob);
			employeeService.add(employee);
			System.out.println("Employee added successfully.");

		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void updateEmployee(Scanner scanner) {
		System.out.print("Enter Employee ID to update: ");
		Long id = scanner.nextLong();
		scanner.nextLine();
		try {
			employeeService.findById(id);
			System.out.print("Enter New Name: ");
			String name = scanner.nextLine();

			System.out.print("Enter New Email: ");
			String email = scanner.nextLine();
			if (!isEmailUnique(email, id)) {
				System.out.println("Error: Email ID '" + email + "' is already in use.");
				return;
			}

			System.out.print("Enter New Salary: ");
			double salary = scanner.nextDouble();

			System.out.print("Enter New DOB (dd-mm-yyyy): ");
			String dob = scanner.next();

			Employee employee = new Employee(id, name, email, salary, dob);
			employeeService.update(employee);
			System.out.println("Employee updated successfully.");

		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void deleteEmployee(Scanner scanner) {
		System.out.print("Enter Employee ID to delete: ");
		Long id = scanner.nextLong();
		try {
			employeeService.delete(id);
			System.out.println("Employee deleted successfully.");
		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void findEmployeeById(Scanner scanner) {
		System.out.print("Enter Employee ID to find: ");
		Long id = scanner.nextLong();
		try {
			Employee employee = employeeService.findById(id);
			System.out.println("Employee Details: " + employee);
		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void listAllEmployees() {
		List<Employee> employees = employeeService.findAll();
		employees.forEach(System.out::println);
	}

	private static boolean isEmailUnique(String email, Long id) {
		Employee employee = employeeService.findByEmail(email);
		return employee == null || employee.getId().equals(id);
	}

}
