package net.texala.employee.service.impl;

import static net.texala.employee.constant.Constants.ERROR_EMPLOYEE_NOT_FOUND;

import java.util.List;
import java.util.Objects;

import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.exception.ServiceException;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private List<Employee> employeeList;

	{
		employeeList = CsvUtility.readFromCSV();
	}

	@Override
	public List<Employee> findAll() {
		return employeeList;
	}

	@Override
	public Employee fetchById(Long id) {
		return employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst()
				.orElseThrow(() -> new ServiceException(ERROR_EMPLOYEE_NOT_FOUND));
	}

	public Employee findByEmail(String email) {
		return employeeList.stream().filter(emp -> emp.getEmail().equalsIgnoreCase(email)).findAny().orElse(null);
	}

	@Override
	public void add(Employee employee) {
		if (Objects.nonNull(findByEmail(employee.getEmail()))) {
			throw new ServiceException("Email id is invalid");
		}
		employeeList.add(employee);
	}

	@Override
	public void update(Employee employee) {
		Employee temp = findByEmail(employee.getEmail());
		if (Objects.nonNull(temp) && temp.getId() != employee.getId()) {
			throw new ServiceException("Email id is invalid");
		}
		employeeList.add(employee);
	}

	@Override
	public void delete(Long id) {
		if (!employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst().isPresent()) {
			System.err.println(ERROR_EMPLOYEE_NOT_FOUND + id);
			return;
		}
		employeeList.remove(employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst().get());
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