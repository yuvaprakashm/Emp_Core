package net.texala.employee.service;

import java.util.List;

import net.texala.employee.model.Employee;

public interface EmployeeService {

	List<Employee> findAll();

	Employee findById(Long id);

	Employee add(Employee employee);

	Employee update(Employee employee);

	boolean delete(Long id);

	Employee findByEmail(String email);

}
