package net.texala.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.input.util.InputValidator;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private CsvUtility csvUtility = new CsvUtility();

	private InputValidator inputValidator = new InputValidator(null);

	private List<Employee> employeeList = new ArrayList<>();

	public EmployeeServiceImpl() {
		inputValidator = new InputValidator(this);
		employeeList = csvUtility.readEmployeesFromCsv();
	}

	  @Override
	    public List<Employee> findAll() {
	        return employeeList;  
	    }

	@Override
	public void fetchById(Long id) {
		Optional<Employee> employee = employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
		if (employee.isPresent()) {
			System.out.println("Employee Details: " + employee.get());
		} else {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
		}
	}

	public Employee findByEmail(String email) {
		return employeeList.stream().filter(emp -> emp.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
	}

	public boolean isEmailUnique(String email, Long id) {
		Employee employee = findByEmail(email);
		return employee == null || employee.getId().equals(id);
	}


	  @Override
	    public void add(Employee employee) {
	        if (employee == null) {
	            return;  
	        }
	        employeeList.add(employee);
	        csvUtility.writeToCsv(employeeList);
	        System.out.println("Employee added successfully");
	    }
	@Override
	public void update(Employee employee) {
		Optional<Employee> existingEmployee = employeeList.stream().filter(emp -> emp.getId().equals(employee.getId())).findFirst();
		if (!existingEmployee.isPresent()) {
			return;
		}
		employeeList.remove(existingEmployee.get());
		employeeList.add(employee);
		csvUtility.writeToCsv(employeeList);
		System.out.println("Employee updated successfully:");
	}

	@Override
	public void delete(Long id) {
		Optional<Employee> employeeToDelete = employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
		if (!employeeToDelete.isPresent()) {
			System.out.println("Error: Employee with ID '" + id + "' not found.");
			return;
		}
		employeeList.remove(employeeToDelete.get());
		csvUtility.writeToCsv(employeeList);
		System.out.println("Employee deleted successfully.");
	}
 
}