package net.texala.employee.web.rest;

import java.util.Scanner;

import net.texala.employee.input.util.InputValidator;
import net.texala.employee.model.Employee;
import net.texala.employee.service.impl.EmployeeServiceImpl;

public class EmployeeController {

	private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
	private static InputValidator inputValidator = new InputValidator(employeeService);

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nChoose Operation :");
			inputValidator.display();
			System.out.print("Enter your choice: ");

			if (scanner.hasNextInt()) {
				int option = scanner.nextInt();
				scanner.nextLine();
				switch (option) {
				case 1:
				    employeeService.add(inputValidator.collectEmployeeData(scanner, inputValidator.promptForLong(scanner, "Enter Employee ID to Add: ")));
				    break;
				case 2:
				    Employee updatedEmployee = inputValidator.collectEmployeeData(scanner,inputValidator.promptForLong(scanner, "Enter Employee ID to update: "), true);
				    if (updatedEmployee != null) employeeService.update(updatedEmployee);
				    break;
				case 3:
					employeeService.delete(inputValidator.promptForLong(scanner, "Enter Employee ID to delete: "));
					break;
				case 4:
					employeeService.fetchById(inputValidator.promptForLong(scanner, "Enter Employee ID to find: "));
					break;
				case 5:
					employeeService.findAll();
					break;
				case 6:
					System.out.println("Exiting...");
					scanner.close();
					return;
				}
			} else {
				System.out.println("Error: Invalid input. Please choose the correct option.");
				scanner.nextLine();
			}
		}
	}
}