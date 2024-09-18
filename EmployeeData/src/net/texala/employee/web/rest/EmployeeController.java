package net.texala.employee.web.rest;

import java.util.List;
import java.util.Optional;
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

			String email;
			boolean isUnique;
			do {
				System.out.print("Enter New Email: ");
				email = scanner.nextLine();
				isUnique = isEmailUnique(email, id);
				if (!isUnique) {
					System.out.println(
							"Error: Email ID '" + email + "' is already in use. Please enter a different email.");
				}
			} while (!isUnique);
			System.out.print("Enter Salary: ");
			double salary = scanner.nextDouble();
			scanner.nextLine();

			System.out.print("Enter DOB (dd-mm-yyyy): ");
			String dob = scanner.nextLine();

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
			Employee existingEmployee = employeeService.findById(id);
			System.out.print("Enter New Name: ");
			String name = scanner.nextLine();
			String email;
			boolean isUnique;
			do {
				System.out.print("Enter New Email: ");
				email = scanner.nextLine();
				isUnique = isEmailUnique(email, id);
				if (!isUnique) {
					System.out.println(
							"Error: Email ID '" + email + "' is already in use. Please enter a different email.");
				}
			} while (!isUnique);
			System.out.print("Enter New Salary: ");
			double salary = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Enter New DOB (dd-mm-yyyy): ");
			String dob = scanner.nextLine();
			Employee updatedEmployee = new Employee(id, name, email, salary, dob);
			employeeService.update(updatedEmployee);
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
 
//package net.texala.employee.web.rest;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//
//import net.texala.employee.exception.Exception.ServiceException;
//import net.texala.employee.model.Employee;
//import net.texala.employee.service.EmployeeService;
//import net.texala.employee.service.impl.EmployeeServiceImpl;
//
//public class EmployeeController {
//
//    private static EmployeeService employeeService = new EmployeeServiceImpl();
//
//    public static void main(String[] args) {
//        try (Scanner scanner = new Scanner(System.in)) {
//            int option;
//            do {
//                option = displayMenu(scanner);
//                handleOption(option, scanner);
//            } while (option != 6);
//        }
//    }
//
//    private static int displayMenu(Scanner scanner) {
//        System.out.println("\nChoose Operation:");
//        System.out.println("1. Add Employee");
//        System.out.println("2. Update Employee");
//        System.out.println("3. Delete Employee");
//        System.out.println("4. Find Employee by ID");
//        System.out.println("5. Display all Employees");
//        System.out.println("6. Exit");
//        System.out.print("Enter your choice: ");
//        return scanner.nextInt();
//    }
//
//    private static void handleOption(int option, Scanner scanner) {
//    	switch (option) {
//		case 1:
//			addEmployee(scanner);
//			break;
//		case 2:
//			updateEmployee(scanner);
//			break;
//		case 3:
//			deleteEmployee(scanner);
//			break;
//		case 4:
//			findEmployeeById(scanner);
//			break;
//		case 5:
//			listAllEmployees();
//			break;
//		case 6:
//			System.out.println("Exiting...");
//			break;
//		default:
//			System.out.println("Invalid choice! Try again.");
//		}
//    }
//
//    private static void addEmployee(Scanner scanner) {
//        Employee employee = createEmployeeFromInput(scanner);
//        if (employee != null) {
//            try {
//                employeeService.add(employee);
//                System.out.println("Employee added successfully.");
//            } catch (ServiceException e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//
//    private static void updateEmployee(Scanner scanner) {
//        System.out.print("Enter Employee ID to update: ");
//        Long id = scanner.nextLong();
//        Employee existingEmployee = employeeService.findById(id);
//        if (existingEmployee == null) {
//            System.out.println("Employee not found.");
//            return;
//        }
//        
//        Employee updatedEmployee = createEmployeeFromInput(scanner);
//        if (updatedEmployee != null) {
//            try {
//                employeeService.update(updatedEmployee);
//                System.out.println("Employee updated successfully.");
//            } catch (ServiceException e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//
//    private static void deleteEmployee(Scanner scanner) {
//        System.out.print("Enter Employee ID to delete: ");
//        Long id = scanner.nextLong();
//        try {
//            employeeService.delete(id);
//            System.out.println("Employee deleted successfully.");
//        } catch (ServiceException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//
//    private static void findEmployeeById(Scanner scanner) {
//        System.out.print("Enter Employee ID to find: ");
//        Long id = scanner.nextLong();
//        try {
//            Employee employee = employeeService.findById(id);
//            if (employee != null) {
//                System.out.println("Employee Details: " + employee);
//            } else {
//                System.out.println("Employee not found.");
//            }
//        } catch (ServiceException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//
//    private static void listAllEmployees() {
//        List<Employee> employees = employeeService.findAll();
//        if (employees.isEmpty()) {
//            System.out.println("No employees found.");
//        } else {
//            employees.forEach(System.out::println);
//        }
//    }
//
//    private static Employee createEmployeeFromInput(Scanner scanner) {
//        System.out.print("Enter Employee ID: ");
//        Long id = scanner.nextLong();
//        scanner.nextLine();
//
//        if (employeeService.findById(id) != null) {
//            System.out.println("Error: Employee with ID '" + id + "' already exists.");
//            return null;
//        }
//
//        System.out.print("Enter Name: ");
//        String name = scanner.nextLine();
//        String email = getUniqueEmail(scanner, id);
//        if (email == null) return null;
//
//        System.out.print("Enter Salary: ");
//        double salary = scanner.nextDouble();
//        scanner.nextLine();
//
//        System.out.print("Enter DOB (dd-mm-yyyy): ");
//        String dob = scanner.nextLine();
//
//        return new Employee(id, name, email, salary, dob);
//    }
//
//    private static String getUniqueEmail(Scanner scanner, Long id) {
//        String email;
//        boolean isUnique;
//        do {
//            System.out.print("Enter New Email: ");
//            email = scanner.nextLine();
//            isUnique = isEmailUnique(email, id);
//            if (!isUnique) {
//                System.out.println("Error: Email ID '" + email + "' is already in use. Please enter a different email.");
//            }
//        } while (!isUnique);
//        return email;
//    }
//
//    private static boolean isEmailUnique(String email, Long id) {
//		Employee employee = employeeService.findByEmail(email);
//		return employee == null || employee.getId().equals(id);
//	}
//}