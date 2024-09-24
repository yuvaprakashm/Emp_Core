package net.texala.employee.service;

import java.util.List;

import net.texala.employee.model.Employee;

public interface EmployeeService {

	List<Employee> findAll();

	void fetchById(Long id);

	void add(Employee employee);

	void update(Employee employee);

	void delete(Long id);

}
