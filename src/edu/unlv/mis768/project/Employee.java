package edu.unlv.mis768.project;

/**
 * @author Luis Moller
 *
 */
public class Employee {
	//fields 
	private String firstName;
	private String lastName;
	private int id;
	
	public Employee(String firstName, String lastName, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setlastName(String name) {
		this.lastName = name;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
