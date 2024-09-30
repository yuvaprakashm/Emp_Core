package net.texala.employee.web.rest;

import static net.texala.employee.constant.Constants.CHOOSE_OPERATION;
import static net.texala.employee.constant.Constants.ENTER_YOUR_CHOICE;
import static net.texala.employee.constant.Constants.ERROR_INVALID_OPTION;
import static net.texala.employee.constant.Constants.EXITTING;

import java.util.Scanner;

import net.texala.employee.enums.OptionType;
import net.texala.employee.input.util.Utility;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.service.impl.EmployeeServiceImpl;

public class EmployeeController {

    private final EmployeeService employeeService;
    private final Scanner scanner;

    public EmployeeController() {
        this.employeeService = new EmployeeServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        EmployeeController controller = new EmployeeController();
        controller.run();
    }

    public void run() {
        while (true) {
            System.out.println(CHOOSE_OPERATION);
            System.out.print(ENTER_YOUR_CHOICE);
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                OptionType option = getOptionType(choice);
                if (option != null) {
                    handleOption(option);
                } else {
                    System.out.println(ERROR_INVALID_OPTION);
                }
            } else {
                System.out.println(ERROR_INVALID_OPTION);
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private OptionType getOptionType(int choice) {
        try {
            return OptionType.values()[choice]; // Assuming enum starts from 0
        } catch (ArrayIndexOutOfBoundsException e) {
            return null; // Invalid option
        }
    }

    private void handleOption(OptionType option) {
        switch (option) {
            case FETCHALL:
                employeeService.findAll().forEach(System.out::println);
                break;
            case FETCH:
                fetchById();
                break;
            case ADD:
                add();
                break;
            case UPDATE:
                update();
                break;
            case DELETE:
                deleteById();
                break;
            case DELETEALL:
                employeeService.deleteAll();
                System.out.println("Record deleted successfully");
                break;
            case EXIT:
                exitProgram();
                break;
        }
    }

    private void fetchById() {
        System.out.print("Enter employee Id: ");
        Long id = scanner.nextLong();
        employeeService.fetchById(id).toString();
    }

    private void add() {
        Employee emp = createInput();
        employeeService.add(emp);
        System.out.println("Record added successfully");
    }

    private void update() {
        Employee emp = createInput();
        employeeService.update(emp); // Assuming you have an update method
        System.out.println("Record updated successfully");
    }

    private Employee createInput() {
        Employee emp = new Employee();
        System.out.print("Enter employee Id: ");
        emp.setId(scanner.nextLong());
        System.out.print("Enter employee name: ");
        emp.setName(scanner.next());
        System.out.print("Enter employee email: ");
        emp.setEmail(scanner.next());
        System.out.print("Enter employee salary: ");
        emp.setSalary(scanner.nextDouble());
        System.out.print("Enter employee DOB (dd-MM-yyyy) format: ");
        String dob = scanner.next();
        emp.setDob(Utility.toLocalDate(dob));
        return emp;
    }

    private void deleteById() {
        System.out.print("Enter employee Id: ");
        Long id = scanner.nextLong();
        employeeService.delete(id);
        System.out.println("Record deleted successfully");
    }

    private void exitProgram() {
    	employeeService.saveAll();
        System.out.println(EXITTING);
        scanner.close();
        System.exit(0); // Exit the program
    }
}
