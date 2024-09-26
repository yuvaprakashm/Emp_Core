package net.texala.employee.csv.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import net.texala.employee.model.Employee;

public class CsvUtility {

	public static final String HEADER = "ID,Name,Email,Salary,DOB";
	
	private static InputStream loadResource() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		return classloader.getResourceAsStream("employees.csv");
	}

	public static List<Employee> readEmployeesFromCSV() {
		List<Employee> employees = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(loadResource()))) {
			String line = reader.readLine(); // Skip header line
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				Employee employee = new Employee(Long.parseLong(data[0]), data[1], data[2], Double.parseDouble(data[3]),
						LocalDate.parse(data[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				employees.add(employee);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return employees;
	}

	public static void writeDataToCSV(List<Employee> employees) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(""))) {
			// Write header
			writer.write(HEADER);
			writer.newLine();
			// Write employee data
			for (Employee employee : employees) {
				writer.write(employee.toString());
				writer.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
