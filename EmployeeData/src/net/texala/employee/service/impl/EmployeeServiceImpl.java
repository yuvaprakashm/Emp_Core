package net.texala.employee.service.impl;

import static net.texala.employee.constant.Constants.EMPLOYEE_DELETED;
import static net.texala.employee.constant.Constants.EMPLOYEE_DETAILS;
import static net.texala.employee.constant.Constants.EMPLOYEE_UPDATED;
import static net.texala.employee.constant.Constants.ERROR_EMPLOYEE_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private List<Employee> employeeList;

	{
		employeeList = CsvUtility.readEmployeesFromCSV();
	}

	@Override
	public List<Employee> findAll() {
		employeeList.forEach(emp -> System.out.println(emp));
		return employeeList;
	}

	@Override
	public void fetchById(Long id) {
		Optional<Employee> employee = employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
		if (employee.isPresent()) {
			System.out.println(EMPLOYEE_DETAILS + employee.get());
		} else {
			System.err.println(ERROR_EMPLOYEE_NOT_FOUND + id);
		}
	}

	public boolean existsId(Long id) {
		return employeeList.stream().anyMatch(emp -> emp.getId().equals(id));
	}

	public Employee getEmail(String email) {
		return employeeList.stream().filter(emp -> emp.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
	}

	@Override
	public void add(Employee employee) {
		employeeList.add(employee);
	}

	@Override
	public void update(Employee employee) {
		employeeList.add(employee);
		System.out.println(EMPLOYEE_UPDATED);
	}

	@Override
	public void delete(Long id) {
		if (!employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst().isPresent()) {
			System.err.println(ERROR_EMPLOYEE_NOT_FOUND + id);
			return;
		}
		employeeList.remove(employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst().get());
		System.out.println(EMPLOYEE_DELETED);
	}

	@Override
	public void deleteAll() {
		employeeList.clear();
	}

	@Override
	public void saveAll() {
		CsvUtility.writeDataToCSV(employeeList);
		
	}
}