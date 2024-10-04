package net.texala.employee.service.impl;

import static net.texala.employee.constant.Constants.EMAIL_ID_INVALID;
import static net.texala.employee.constant.Constants.EMPLOYEE_ID_ALREADY_EXISTS;
import static net.texala.employee.constant.Constants.EMPLOYEE_ID_NOT_EXISTS;
import static net.texala.employee.constant.Constants.ERROR_EMPLOYEE_NOT_FOUND;

import java.util.Map;
import java.util.Optional;

import net.texala.employee.csv.util.CsvUtility;
import net.texala.employee.exception.ServiceException;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private Map<Long, Employee> employeeMap;

	{
		employeeMap = CsvUtility.readFromCSV();
	}

	@Override
	public Map<Long, Employee> findAll() {
		return employeeMap;
	}

	@Override
	public Employee fetchById(Long id) {
		return Optional.ofNullable(employeeMap.get(id))
				.orElseThrow(() -> new ServiceException(ERROR_EMPLOYEE_NOT_FOUND));
	}

	public Employee findByEmail(String email) {
		return employeeMap.values().stream().filter(emp -> emp.getEmail().equalsIgnoreCase(email)).findAny()
				.orElse(null);
	}

	@Override
	public void add(Employee employee) {
		if (employeeMap.containsKey(employee.getId())) {// To insure id
			throw new ServiceException(EMPLOYEE_ID_ALREADY_EXISTS);
		}
		if (findByEmail(employee.getEmail()) != null) {
			throw new ServiceException(EMAIL_ID_INVALID);
		}
		employeeMap.put(employee.getId(), employee);
	}

	@Override
	public void update(Employee employee) {
		if (!employeeMap.containsKey(employee.getId())) {
			throw new ServiceException(EMPLOYEE_ID_NOT_EXISTS);
		}
		Employee temp = findByEmail(employee.getEmail());
		if (temp != null && !temp.getId().equals(employee.getId())) {
			throw new ServiceException(EMAIL_ID_INVALID);
		}
		employeeMap.put(employee.getId(), employee);
	}

	@Override
	public void delete(Long id) {
		if (!employeeMap.containsKey(id)) {
			System.err.println(ERROR_EMPLOYEE_NOT_FOUND + id);
			return;
		}
		employeeMap.remove(id);
	}

	@Override
	public void deleteAll() {
		employeeMap.clear();
	}

	@Override
	public void saveAll() {
		CsvUtility.writeDataToCSV(employeeMap);
	}

}