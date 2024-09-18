package net.texala.employee.service.impl;

import java.util.List;

import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.util.CsvUtility;

public class EmployeeServiceImpl implements EmployeeService {

	private CsvUtility csvUtility = new CsvUtility();

	@Override
	public List<Employee> findAll() {
		return csvUtility.findAll();
	}

	@Override
	public Employee findById(Long id) {
		return csvUtility.findById(id)
				.orElseThrow(() -> new ServiceException("Employee with ID '" + id + "' not found."));
	}

	@Override
	public Employee add(Employee employee) {
		if (csvUtility.findById(employee.getId()).isPresent()) {
			throw new ServiceException("Employee with ID '" + employee.getId() + "' already exists.");
		}
		csvUtility.add(employee);
		return employee;
	}

	@Override
	public Employee update(Employee employee) {
		csvUtility.findById(employee.getId())
				.orElseThrow(() -> new ServiceException("Employee with ID '" + employee.getId() + "' not found."));
		csvUtility.update(employee);
		return employee;
	}

	@Override
	public boolean delete(Long id) {
		csvUtility.findById(id).orElseThrow(() -> new ServiceException("Employee with ID '" + id + "' not found."));
		csvUtility.delete(id);
		return true;
	}

	@Override
	public Employee findByEmail(String email) {
	    return csvUtility.findAll().stream()
	        .filter(emp -> emp.getEmail().equalsIgnoreCase(email))
	        .findFirst()
	        .orElse(null);  
	}


	@Override
	public Employee findId(Long id) {
		return csvUtility.findById(id).orElse(null);
	}
}
