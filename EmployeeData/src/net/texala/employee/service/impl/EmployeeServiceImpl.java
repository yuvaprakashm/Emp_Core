package net.texala.employee.service.impl;

import java.util.List;
import java.util.Scanner;
import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.input.util.InputValidator;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private CsvUtility csvUtility = new CsvUtility();

	private InputValidator inputValidator = new InputValidator();

	@Override
	public void findAll() {
		List<Employee> employees = csvUtility.findAll();
		employees.forEach(System.out::println);
	}

	@Override
	public void findById(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID to find: ");
		Employee employee = csvUtility.findById(id).orElse(null);
		if (employee != null) {
			System.out.println("Employee Details: " + employee);
		} else {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
		}
	}

	public Employee findById(Long id) {
		return csvUtility.findById(id).orElse(null);
	}

	@Override
	public void add(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID: ");
		if (findById(id) != null) {
			System.out.println("Error: Employee with ID '" + id + "' already exists.");
			return;
		}
		csvUtility.add(inputValidator.collectEmployeeData(scanner, id));
		System.out.println("Employee added successfully");

	}

	@Override
	public void update(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID to update: ");
		Employee existingEmployee = findById(id);
		if (existingEmployee == null) {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
			return;
		}
		csvUtility.update(inputValidator.collectEmployeeData(scanner, id));
		System.out.println("Employee updated successfully:");
	}

	@Override
	public void delete(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID to delete: ");
		try {
			csvUtility.delete(id);
			System.out.println("Employee deleted successfully.");
		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}