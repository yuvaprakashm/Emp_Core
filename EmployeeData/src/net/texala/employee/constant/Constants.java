package net.texala.employee.constant;

public class Constants {
		
    public static final String ERROR_INVALID_INPUT = "Error! Invalid input. Please enter a valid number.";
    public static final String ERROR_SALARY_POSITIVE = "Error! Salary must be positive.";
    public static final String ERROR_EMAIL_INVALID = "Error! Invalid email format. Please enter a valid email.";
    public static final String ERROR_EMAIL_IN_USE = "Error: Email ID is already in use : ";
    public static final String ERROR_EMPLOYEE_NOT_FOUND = "Error! Employee with ID not found : ";
    public static final String ERROR_EMPLOYEE_EXISTS = "Error! Employee with ID already exists : ";
    public static final String ERROR_STRING_INVALID = "Invalid input. Please enter only alphabets.";
    public static final String ERROR_DATE_INVALID ="Error! Invalid date format. Please enter the date in 'dd-MM-yyyy' format.";
    public static final String ENTER_DATE = "Enter DOB (dd-MM-yyyy): ";
    public static final String ENTER_NAME = "Enter Name: ";
    public static final String ENTER_NEW_EMAIL ="Enter New Email: ";
    public static final String ENTER_SALARY = "Enter Salary: ";
    
    
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String STRING_REGEX = "[a-zA-Z]+";

    
    public static final String FILE_PATH = "resources/employees.csv";
    public static final String CSV_HEADER = "ID,Name,Email,Salary,DOB";
    
    
    public static final String ERROR_CSV_FILE_NOT_FOUND = "CSV file not found.";
    public static final String ERROR_INVALID_NUMBER = "Invalid number format in CSV data:";
    public static final String ERROR_READING_CSV = "Error! reading CSV file: ";
    public static final String ERROR_WRITING_CSV = "Error! writing to CSV file: ";
    
    
    public static final String CHOOSE_OPERATION = "\nChoose Operation :";
    public static final String ENTER_YOUR_CHOICE = "Enter your choice: ";
    public static final String ERROR_INVALID_OPTION = "Error! Invalid input. Please choose the correct option.";
    public static final String EXITTING = "Exiting...";
    
    public static final String ADD_EMPLOYEE = "Enter Employee ID to Add: ";
    public static final String UPDATE_EMPLOYEE = "Enter Employee ID to update: ";
    public static final String DELETE_EMPLOYEE = "Enter Employee ID to delete: ";
    public static final String FETCH_EMPLOYEE = "Enter Employee ID to find: ";
    
    public static final String EMPLOYEE_ADDED = "Employee added successfully.";
    public static final String EMPLOYEE_UPDATED = "Employee updated successfully:";
    public static final String EMPLOYEE_DELETED = "Employee deleted successfully.";
    public static final String EMPLOYEE_DETAILS = "Employee Details: ";
	 
    
}
