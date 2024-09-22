package net.texala.employee.csv.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.texala.employee.model.Employee;

public class CsvUtility {

	private static final String FILE_PATH = "resources/employees.csv";

	private static final String CSV_HEADER = "ID,Name,Email,Salary,DOB";

	public List<Employee> readEmployeesFromCsv() {
		Path path = Paths.get(FILE_PATH);
		if (!Files.exists(path)) {
			System.err.println("CSV file not found.");
			return new ArrayList<>();
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
			return new ArrayList<>();
		}
	}

	public void writeToCsv(List<Employee> employees) {
		Path path = Paths.get(FILE_PATH);
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(CSV_HEADER);
			bw.newLine();
			for (Employee emp : employees) {
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
