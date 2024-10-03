package net.texala.employee.csv.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import net.texala.employee.constant.Constants;
import net.texala.employee.model.Employee;

public class CsvUtility {
	
	public static final String HEADER = "ID,Name,Email,Salary,DOB";
	
    public static final String FILEPATH = "src/resources/employees.csv";

    
	public static List<Employee> readFromCSV() {
		List<Employee> employees = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILEPATH))))) {
			String line = reader.readLine(); // Skip header line
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				Employee employee = new Employee(Long.parseLong(data[0]), data[1], data[2], Double.parseDouble(data[3]),
						LocalDate.parse(data[4], DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
				employees.add(employee);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return employees;
	}

 
	public static void writeDataToCSV(List<Employee> employees) {
	    try {
	    	Path outputPath = Paths.get(FILEPATH);
	        Files.createDirectories(outputPath.getParent());
	        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
	            writer.write(HEADER);
	            writer.newLine();
	            for (Employee employee : employees) {
	                writer.write(employee.toString());
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
