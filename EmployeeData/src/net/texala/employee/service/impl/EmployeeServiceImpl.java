package net.texala.employee.service.impl;

import java.util.List;

import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.util.CsvUtility;

public class EmployeeServiceImpl implements EmployeeService {

    private CsvUtility csvUtility = new CsvUtility();

    public List<Employee> findAll() {
        return csvUtility.findAll();
    }

    public Employee findById(Long id) {
        return csvUtility.findById(id).orElse(null);
    }

    public Employee add(Employee employee) {
        csvUtility.add(employee);
        return employee;
    }

    public Employee update(Employee employee) {
        csvUtility.update(employee);
        return employee;
    }

	public boolean delete(Long id) {
		csvUtility.delete(id);
		return true;
	}

	public Employee findByEmail(String email) {
	    return csvUtility.findAll().stream()
	        .filter(emp -> emp.getEmail().equalsIgnoreCase(email))
	        .findFirst()
	        .orElse(null);  
	}

}
