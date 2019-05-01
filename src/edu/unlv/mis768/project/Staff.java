package edu.unlv.mis768.project;

public class Staff extends Employee {
	//fields 
	private int accessLevel;
	private String role;
	
	public Staff(String name, int id, String title,
			int accessLevel, String role) {
		this.name = name;
		this.id = id;
		this.title = title;
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
		return "Name: " + getName() +"\n"
				+"ID: " + getId() + "\n"
				+ "Title: " + getTitle() +"\n"
				+ "Role: " + getRole();
	}
	
	
	
	



}
