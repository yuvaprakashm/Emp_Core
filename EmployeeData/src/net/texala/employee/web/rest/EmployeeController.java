package net.texala.employee.web.rest;

import static net.texala.employee.constant.Constants.CHOOSE_OPERATION;
import static net.texala.employee.constant.Constants.ENTER_YOUR_CHOICE;
import static net.texala.employee.constant.Constants.ERROR_INVALID_OPTION;
import static net.texala.employee.constant.Constants.EXITTING;
import static net.texala.employee.constant.Constants.ENTER_EMPLOYEE_ID;
import static net.texala.employee.constant.Constants.ENTER_EMPLOYEE_NAME;
import static net.texala.employee.constant.Constants.ENTER_EMPLOYEE_DOB;
import static net.texala.employee.constant.Constants.ENTER_EMPLOYEE_EMAIL;
import static net.texala.employee.constant.Constants.ENTER_EMPLOYEE_SALARY;
import static net.texala.employee.constant.Constants.RECORD_DELETED_SUCCESSFULLY;
import static net.texala.employee.constant.Constants.RECORD_UPDATED_SUCCESSFULLY;
import static net.texala.employee.constant.Constants.RECORD_ADDED_SUCCESSFULLY;
import java.util.Scanner;

import net.texala.employee.enums.OptionType;
import net.texala.employee.input.util.Utility;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.service.impl.EmployeeServiceImpl;

public class EmployeeController {

	private final EmployeeService employeeService;
	private final Scanner scanner;

	public EmployeeController() {
		this.employeeService = new EmployeeServiceImpl();
		this.scanner = new Scanner(System.in);
	}

	public static void main(String[] args) {
		EmployeeController controller = new EmployeeController();
		controller.run();
	}

	public void run() {
		while (true) {
			System.out.println(CHOOSE_OPERATION);
			printAllOptionTypes();
			System.out.print(ENTER_YOUR_CHOICE);
			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();// Need to print all operation types
				OptionType option = getOptionType(choice);
				if (option != null) {
					handleOption(option);
				} else {
					System.out.println(ERROR_INVALID_OPTION);
				}
			} else {
				System.out.println(ERROR_INVALID_OPTION);
				scanner.nextLine(); // Clear invalid input
			}
		}
	}

	private void printAllOptionTypes() {
		int index = 1;
		System.out.println();
		for (OptionType option : OptionType.values()) {
			System.out.println(index + ". " + option);
			index++;
		}
	}

	private OptionType getOptionType(int choice) {
		try {
			return OptionType.values()[choice - 1];  
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;  
		}
	}

	private void handleOption(OptionType option) {
		switch (option) {
		case FETCHALL:
			employeeService.findAll().forEach(System.out::println);
			break;
		case FETCH:
			fetchById();
			break;
		case ADD:
			add();
			break;
		case UPDATE:
			update();
			break;
		case DELETE:
			deleteById();
			break;
		case DELETEALL:
			employeeService.deleteAll();
			System.out.println(RECORD_DELETED_SUCCESSFULLY);
			break;
		case EXIT:
			exitProgram();
			break;
		}
	}

	private void fetchById() {
		System.out.print(ENTER_EMPLOYEE_ID);
		Long id = scanner.nextLong();
		Employee emp = employeeService.fetchById(id);
		System.out.println(emp.toString());
	}

	private void add() {
		Employee emp = createInput();
		employeeService.add(emp);
		System.out.println(RECORD_ADDED_SUCCESSFULLY);
	}

	private void update() {
		Employee emp = createInput();
		employeeService.update(emp);  
		System.out.println(RECORD_UPDATED_SUCCESSFULLY);
	}

	private Employee createInput() {
		Employee emp = new Employee();
		System.out.print(ENTER_EMPLOYEE_ID);
		emp.setId(scanner.nextLong());
		System.out.print(ENTER_EMPLOYEE_NAME);
		emp.setName(scanner.next());
		System.out.print(ENTER_EMPLOYEE_EMAIL);
		emp.setEmail(scanner.next());
		System.out.print(ENTER_EMPLOYEE_SALARY);
		emp.setSalary(Utility.hasSalary(scanner));
		System.out.print(ENTER_EMPLOYEE_DOB);
		emp.setDob(Utility.hasDate(Utility.toLocalDate(scanner.next()))); 
		return emp;
	}

	private void deleteById() {
		System.out.print(ENTER_EMPLOYEE_ID);
		Long id = scanner.nextLong();
		employeeService.delete(id);
		System.out.println(RECORD_DELETED_SUCCESSFULLY);
	}

	private void exitProgram() {
		employeeService.saveAll();
		System.out.println(EXITTING);
		scanner.close();
		System.exit(0);  
	}
}
