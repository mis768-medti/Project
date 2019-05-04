package edu.unlv.mis768.project;

public class Staff extends Employee {
	//fields 
	private int accessLevel;
	private String role;
	
	//Constructor
	public Staff(String firstName, String lastName, int id,
			int accessLevel, String role) {
		
		super(firstName, lastName, id);
		this.accessLevel = accessLevel;
		this.role = role;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String toString() {
		return "Name: " + getFirstName() + " " + getLastName() +"\n"
				+"ID: " + getId() + "\n"
				+ "Role: " + getRole();
	}
	
	
	
	



}
