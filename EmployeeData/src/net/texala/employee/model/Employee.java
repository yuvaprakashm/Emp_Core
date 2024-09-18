package net.texala.employee.model;

public class Employee {

	private Long id;
	private String name;
	private String email;
	private double salary;
	private String dob;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Employee() {
	}

	public Employee(Long id, String name, String email, double salary, String dob) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.salary = salary;
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Employee [ID = " + id + ", Name = " + name + ", Email = " + email + ", Salary = " + salary + ", DOB = "
				+ dob + "]";
	}

}
