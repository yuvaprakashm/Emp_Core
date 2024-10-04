package net.texala.employee.service;

import java.util.Map;

import net.texala.employee.model.Employee;

public interface EmployeeService {

	Map<Long, Employee> findAll();

	Employee fetchById(Long id);

	void add(Employee employee);

	void update(Employee employee);

	void saveAll();

	void delete(Long id);

	void deleteAll();

}
