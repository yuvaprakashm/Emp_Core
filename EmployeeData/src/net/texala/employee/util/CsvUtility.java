package net.texala.employee.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.model.Employee;

public class CsvUtility {

	private static final String FILE_PATH = "resources/employees.csv";
	private static final String CSV_HEADER = "ID,Name,Email,Salary,DOB";
	private List<Employee> employeeCache = new ArrayList<>();

	public CsvUtility() {
		loadEmployees();
	}

	private void loadEmployees() {
		employeeCache = readEmployeesFromCsv();
	}

	public List<Employee> findAll() {
		return new ArrayList<>(employeeCache);
	}

	public Optional<Employee> findById(Long id) {
		return employeeCache.stream().filter(emp -> emp.getId().equals(id)).findFirst();
	}

	public void add(Employee employee) {
		employeeCache.add(employee);
		writeToCsv();
	}

	public void update(Employee employee) {
		Employee existingEmployee = findById(employee.getId())
				.orElseThrow(() -> new ServiceException("Employee with ID '" + employee.getId() + "' not found."));
		employeeCache.remove(existingEmployee);
		employeeCache.add(employee);
		writeToCsv();
	}

	public void delete(Long id) {
		Employee employeeToRemove = findById(id)
				.orElseThrow(() -> new ServiceException("Employee with ID '" + id + "' not found."));
		employeeCache.remove(employeeToRemove);
		writeToCsv();
	}

	private List<Employee> readEmployeesFromCsv() {
		Path path = Paths.get(FILE_PATH);
		if (!Files.exists(path)) {
			System.err.println("CSV file not found.");
			return Collections.emptyList();
		}
		try (BufferedReader br = Files.newBufferedReader(path)) {
			br.readLine();
			return br.lines().map(line -> {
				String[] data = line.split(",", -1);
				try {
					Employee emp = new Employee();
					emp.setId(Long.parseLong(data[0]));
					emp.setName(data[1]);
					emp.setEmail(data[2]);
					emp.setSalary(Double.parseDouble(data[3]));
					emp.setDob(LocalDate.parse(data[4], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
					return emp;
				} catch (NumberFormatException e) {
					System.err.println("Invalid number format in CSV data: " + e.getMessage());
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("Error reading CSV file: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	private void writeToCsv() {
		Path path = Paths.get(FILE_PATH);
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(CSV_HEADER);
			bw.newLine();
			for (Employee emp : employeeCache) {
				bw.write(String.join(",", String.valueOf(emp.getId()), emp.getName(), emp.getEmail(),
						String.valueOf(emp.getSalary()),
						emp.getDob().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}
}
