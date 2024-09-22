package net.texala.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.input.util.InputValidator;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private CsvUtility csvUtility = new CsvUtility();

	private InputValidator inputValidator = new InputValidator(null);

	private List<Employee> employeeCache = new ArrayList<>();

	public EmployeeServiceImpl() {
		inputValidator = new InputValidator(this);
		employeeCache = csvUtility.readEmployeesFromCsv();
	}

	@Override
	public void findAll() {
		if (employeeCache.isEmpty()) {
			System.out.println("No employees found.");
		} else {
			employeeCache.forEach(System.out::println);
		}
	}

	@Override
	public void findById(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID to find: ");
		Optional<Employee> employee = employeeCache.stream().filter(emp -> emp.getId().equals(id)).findFirst();
		if (employee.isPresent()) {
			System.out.println("Employee Details: " + employee.get());
		} else {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
		}
	}

	public Employee findByEmail(String email) {
		return employeeCache.stream().filter(emp -> emp.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
	}

	public boolean isEmailUnique(String email, Long id) {
		Employee employee = findByEmail(email);
		return employee == null || employee.getId().equals(id);
	}

	@Override
	public void add(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID: ");
		if (employeeCache.stream().anyMatch(emp -> emp.getId().equals(id))) {
			System.out.println("Error: Employee with ID '" + id + "' already exists.");
			return;
		}
		employeeCache.add(inputValidator.collectEmployeeData(scanner, id));
		csvUtility.writeToCsv(employeeCache);
		System.out.println("Employee added successfully");

	}

	@Override
	public void update(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID to update: ");
		Optional<Employee> existingEmployee = employeeCache.stream().filter(emp -> emp.getId().equals(id)).findFirst();
		if (!existingEmployee.isPresent()) {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
			return;
		}
		employeeCache.remove(existingEmployee.get());
		employeeCache.add(inputValidator.collectEmployeeData(scanner, id));
		csvUtility.writeToCsv(employeeCache);
		System.out.println("Employee updated successfully:");
	}

	@Override
	public void delete(Scanner scanner) {
		Long id = inputValidator.promptForLong(scanner, "Enter Employee ID to delete: ");
		Optional<Employee> employeeToDelete = employeeCache.stream().filter(emp -> emp.getId().equals(id)).findFirst();
		if (!employeeToDelete.isPresent()) {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
			return;
		}
		employeeCache.remove(employeeToDelete.get());
		csvUtility.writeToCsv(employeeCache);
		System.out.println("Employee deleted successfully.");
	}
 
}