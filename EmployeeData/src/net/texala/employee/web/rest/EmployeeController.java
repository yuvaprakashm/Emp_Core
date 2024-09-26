package net.texala.employee.web.rest;

import java.util.Scanner;
import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.input.util.Utility;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.service.impl.EmployeeServiceImpl;
import static net.texala.employee.constant.Constants.ERROR_INVALID_OPTION;
import static net.texala.employee.constant.Constants.EXITTING;
import static net.texala.employee.constant.Constants.CHOOSE_OPERATION;
import static net.texala.employee.constant.Constants.ENTER_YOUR_CHOICE;
import static net.texala.employee.constant.Constants.ADD_EMPLOYEE;
import static net.texala.employee.constant.Constants.UPDATE_EMPLOYEE;
import static net.texala.employee.constant.Constants.DELETE_EMPLOYEE;
import static net.texala.employee.constant.Constants.FETCH_EMPLOYEE;

public class EmployeeController {

	private static final EmployeeService employeeService = new EmployeeServiceImpl();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		while (true) {  //change if 
			System.out.println(CHOOSE_OPERATION);
			System.out.print(ENTER_YOUR_CHOICE);
			if (scanner.hasNextInt()) {
				int option = scanner.nextInt();
				scanner.nextLine();
				switch (option) {
				case 1://use enum 
					employeeService.add(inputValidator.add(scanner, inputValidator.Long(scanner, ADD_EMPLOYEE)));
					break;
				case 2:
					employeeService.update(inputValidator.update(scanner, inputValidator.Long(scanner, UPDATE_EMPLOYEE)));
					break;
				case 3:
					employeeService.delete(inputValidator.Long(scanner, DELETE_EMPLOYEE));
					break;
				case 4:
					employeeService.fetchById(inputValidator.Long(scanner, FETCH_EMPLOYEE));
					break;
				case 5:
					employeeService.findAll();
					break;
				case 6:
					System.out.println(EXITTING);
					scanner.close();
					return;
				}
			} else {
				System.out.println(ERROR_INVALID_OPTION);
				scanner.nextLine();
			}
		}
	}
}