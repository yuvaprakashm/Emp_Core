package net.texala.employee.web.rest;

import java.util.Scanner;

import net.texala.employee.enums.Operation;
import net.texala.employee.service.impl.EmployeeServiceImpl;

public class EmployeeController {

	private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int option = 0;

		while (option != Operation.EXIT.getValue()) {
			System.out.println("\nChoose Operation :");
			for (Operation operation : Operation.values()) {
				System.out.println(operation.getValue() + ". " + operation.getDescription());
			}
			System.out.print("Enter your choice: ");

			if (scanner.hasNextInt()) {
				option = scanner.nextInt();
				scanner.nextLine();
				try {
					Operation operation = Operation.fromValue(option);
					switch (operation) {
					case ADD:
						employeeService.add(scanner);
						break;
					case UPDATE:
						employeeService.update(scanner);
						break;
					case DELETE:
						employeeService.delete(scanner);
						break;
					case FIND_BY_ID:
						employeeService.findById(scanner);
						break;
					case FETCH_ALL:
						employeeService.findAll();
						break;
					case EXIT:
						System.out.println("Exiting...");
						break;
					}
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid choice! Try again.");
				}
			} else {
				System.out.println("Error: Invalid input. Please choose the correct option.");
				scanner.nextLine();
			}
		}
		scanner.close();
	}
}
