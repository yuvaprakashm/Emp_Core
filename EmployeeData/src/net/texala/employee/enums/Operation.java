package net.texala.employee.enums;

public enum Operation {
	ADD(1, "Add"), 
	UPDATE(2, "Update"), 
	DELETE(3, "Delete"), 
	FIND_BY_ID(4, "Find by ID"), 
	FETCH_ALL(5, "fetch all"),
	EXIT(6, "Exit");

	private final int value;
	
	private final String name;

	Operation(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return name;
	}

	public static Operation fromValue(int value) {
		for (Operation operation : values()) {
			if (operation.getValue() == value) {
				return operation;
			}
		}
		throw new IllegalArgumentException("Invalid operation : " + value);
	}
}