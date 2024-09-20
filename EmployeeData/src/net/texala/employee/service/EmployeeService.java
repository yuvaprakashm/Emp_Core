package net.texala.employee.service;

import java.util.Scanner;

public interface EmployeeService {

	void findAll();

	void findById(Scanner scanner);

	void add(Scanner scanner);

	void update(Scanner scanner);

	void delete(Scanner scanner);

}
