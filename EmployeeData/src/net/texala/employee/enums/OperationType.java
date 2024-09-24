//package net.texala.employee.enums;
//
//public enum OperationType {//changes in name
//	ADD(1, "Add"), 
//	UPDATE(2, "Update"), 
//	DELETE(3, "Delete"), 
//	FIND_BY_ID(4, "FindbyID"), 
//	FETCH_ALL(5, "FetchAll"),
//	EXIT(6, "Exit");
//
//	private final int value;
//	
//	private final String name;
//
//	OperationType(int value, String name) {
//		this.value = value;
//		this.name = name;
//	}
//
//	public int getValue() {
//		return value;
//	}
//
//	public String getName() { //changes
//		return name;
//	}
//
//	public static OperationType fromValue(int value) {
//		for (OperationType operation : values()) {
//			if (operation.getValue() == value) {
//				return operation;
//			}
//		}
//		throw new IllegalArgumentException("Invalid operation : " + value);
//	}
//}