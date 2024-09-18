package net.texala.employee.service.impl;

import java.util.List;
import java.util.Optional;

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
        findById(employee.getId());  
        csvUtility.update(employee);
        return employee;
    }

	@Override
    public boolean delete(Long id) {
        findById(id);  
        csvUtility.delete(id);
        return true;
    }

	@Override
    public Optional<Employee> findByEmail(String email) {
        return csvUtility.findAll().stream()
                .filter(emp -> emp.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

}
